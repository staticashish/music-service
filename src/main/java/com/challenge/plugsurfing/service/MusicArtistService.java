package com.challenge.plugsurfing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.dto.MusicBrainzArtistRelation;
import com.challenge.plugsurfing.dto.MusicBrainzArtistReleaseGroup;
import com.challenge.plugsurfing.dto.MusicBrainzArtistResponse;
import com.challenge.plugsurfing.dto.WikidataResponse;
import com.challenge.plugsurfing.dto.WikipediaResponse;
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
	
	@Cacheable(
		      value = "musicArtistCache", 
		      key = "#mbid")
	public MusicArtistResponse getMusicArtistInformation(final String mbid) {
		final MusicBrainzArtistResponse artistInformation = musicBrainzClient.getArtistInformationByMBID(mbid);
		final Optional<MusicBrainzArtistRelation> wikidataRelation = artistInformation.getMusicBrainzArtistRelations()
																.stream()
																.filter(r -> r.getType().equalsIgnoreCase(WIKIDATA))
																.findFirst();
		artistInformation.getMusicBrainzArtistReleaseGroups()
				.stream()
				.filter(rGroup -> rGroup.getType().equalsIgnoreCase(RELEASE_ALBUM))
				.forEach(rGroup -> rGroup.setArtCover(coverArtArchiveClient.getArtCoverByMBID(rGroup.getMbid())));
		
		log.info("MusicBrainzArtistResponse {}", artistInformation);
		
		
		final String wikidataUrl = wikidataRelation.isPresent() ? wikidataRelation.get().getUrl().getResource() : null;
		log.info("wikidataUrl : {}", wikidataUrl); 
		String wikiId = parseAndGetWikiId(wikidataUrl);
		log.info("wikiId : {}", wikiId);
		
		final WikidataResponse artistWikiDataInformation = wikidataClient.getArtistWikidataInformationByWikiId(wikiId);
		
		final String artistWikiDataTitle = artistWikiDataInformation
				.getEntities()
				.get(wikiId)
				.getSitelinks()
				.get(EN_SITELINKS)
				.getTitle();
		log.info("artistWikiDataTitle : {}", artistWikiDataTitle);
		final WikipediaResponse wikipediaResponse = wikipediaClient.getArtistWikipediaInformationByTitle(artistWikiDataTitle);
		log.info("wikipediaResponse : {}", wikipediaResponse);
		return buildMusicArtistResponse(wikipediaResponse, artistInformation);
	}

	private MusicArtistResponse buildMusicArtistResponse(WikipediaResponse wikipediaResponse, MusicBrainzArtistResponse artistInformation) {

		return MusicArtistResponse.builder()
				.artistTitle(wikipediaResponse.getTitle())
				.artistTitleDesc(wikipediaResponse.getDescription())
				.description(wikipediaResponse.getExtract())
				.albums(artistInformation.getMusicBrainzArtistReleaseGroups())
				.build();
	}

	private String parseAndGetWikiId(String wikidataUrl) {
		return wikidataUrl.substring(wikidataUrl.lastIndexOf("/")+1, wikidataUrl.length());
	}
}
