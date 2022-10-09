package com.challenge.plugsurfing.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import com.challenge.plugsurfing.dto.ArtCover;
import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.dto.MusicBrainzArtistRelation;
import com.challenge.plugsurfing.dto.MusicBrainzArtistReleaseGroup;
import com.challenge.plugsurfing.dto.MusicBrainzArtistResponse;
import com.challenge.plugsurfing.dto.WikidataResponse;
import com.challenge.plugsurfing.dto.WikipediaResponse;
import com.challenge.plugsurfing.exception.DataNotFoundException;
import com.challenge.plugsurfing.external.CoverArtArchiveClient;
import com.challenge.plugsurfing.external.MusicBrainzClient;
import com.challenge.plugsurfing.external.WikidataClient;
import com.challenge.plugsurfing.external.WikipediaClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MusicArtistService {

  private static final String WIKIDATA = "wikidata";
  private static final String RELEASE_ALBUM = "album";
  private static final String EN_SITELINKS = "enwiki";

  @Autowired
  private MusicBrainzClient musicBrainzClient;

  @Autowired
  private WikidataClient wikidataClient;

  @Autowired
  private WikipediaClient wikipediaClient;

  @Autowired
  private CoverArtArchiveClient coverArtArchiveClient;

  @Cacheable(value = "musicArtistCache", key = "#mbid")
  public MusicArtistResponse getMusicArtistInformation(@NonNull final String mbid) {
    String wikidataUrl;
    // Get artistInformation from MusicBrainz
    final MusicBrainzArtistResponse artistInformation = musicBrainzClient.getArtistInformationByMBID(mbid);

    // Filter wikidata relations from artist relation list
    final Optional<MusicBrainzArtistRelation> wikidataRelation =
        artistInformation.getMusicBrainzArtistRelations().stream().filter(r -> r.getType().equalsIgnoreCase(WIKIDATA)).findFirst();

    if (wikidataRelation.isPresent()) {
      wikidataUrl = wikidataRelation.get().getUrl().getResource();
    } else {
      log.error("Wikidata link not found for artist with mbid :{}", mbid);
      throw new DataNotFoundException("wikidata link not found for artist with mbid :" + mbid);
    }

    // Parse and get the wikiId from wikidataUrl
    String wikiId = parseAndGetWikiId(wikidataUrl);

    // Get Wikidata information for artist
    final WikidataResponse artistWikiDataInformation = wikidataClient.getArtistWikidataInformationByWikiId(wikiId);

    // Get Wikipedia information for artist
    final WikipediaResponse wikipediaResponse = getWikipediaInformation(wikiId, artistWikiDataInformation);

    // Get album list with cover art.
    List<MusicBrainzArtistReleaseGroup> releaseGroups = getAlbumsWithLinks(artistInformation);

    // Build response
    return buildMusicArtistResponse(wikipediaResponse, releaseGroups);
  }

  private List<MusicBrainzArtistReleaseGroup> getAlbumsWithLinks(final MusicBrainzArtistResponse artistInformation) {

    List<CompletableFuture<MusicBrainzArtistReleaseGroup>> releaseGroupsFuture = artistInformation.getMusicBrainzArtistReleaseGroups()
        .stream()
        .filter(rg -> rg.getType() != null && rg.getType().equalsIgnoreCase(RELEASE_ALBUM))
        .map(rg -> getArtCoverForReleaseGroup(rg))
        .collect(Collectors.toList());

    CompletableFuture.allOf(releaseGroupsFuture.toArray(new CompletableFuture[0]));

    List<MusicBrainzArtistReleaseGroup> releaseGroups = releaseGroupsFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    return releaseGroups;
  }

  private WikipediaResponse getWikipediaInformation(String wikiId, final WikidataResponse artistWikiDataInformation) {

    if (artistWikiDataInformation.getEntities().isEmpty()) {
      log.error("Wikipedia data not found for artist with wikiId :{}", wikiId);
      throw new NotFoundException("wikidata data not found for artist with wikiId :" + wikiId);
    }

    final String artistWikiDataTitle = artistWikiDataInformation.getEntities().get(wikiId).getSitelinks().get(EN_SITELINKS).getTitle();
    final WikipediaResponse wikipediaResponse = wikipediaClient.getArtistWikipediaInformationByTitle(artistWikiDataTitle);
    return wikipediaResponse;
  }

  private CompletableFuture<MusicBrainzArtistReleaseGroup> getArtCoverForReleaseGroup(MusicBrainzArtistReleaseGroup rg) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    return CompletableFuture.supplyAsync(() -> {
      Optional<ArtCover> artCoverByMBID = coverArtArchiveClient.getArtCoverByMBID(rg.getMbid());
      if (artCoverByMBID.isPresent()) {
        rg.setArtCover(artCoverByMBID.get());
      }
      return rg;
    }, executorService);
  }

  private MusicArtistResponse buildMusicArtistResponse(WikipediaResponse wikipediaResponse, List<MusicBrainzArtistReleaseGroup> releaseGroups) {

    return MusicArtistResponse.builder()
        .artistTitle(wikipediaResponse.getTitle())
        .artistTitleDesc(wikipediaResponse.getDescription())
        .description(wikipediaResponse.getExtract())
        .albums(releaseGroups)
        .build();
  }

  private String parseAndGetWikiId(String wikidataUrl) {
    return wikidataUrl.substring(wikidataUrl.lastIndexOf("/") + 1, wikidataUrl.length());
  }
}
