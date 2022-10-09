package com.challenge.plugsurfing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.challenge.plugsurfing.dto.ArtCover;
import com.challenge.plugsurfing.dto.Image;
import com.challenge.plugsurfing.dto.MusicArtistResponse;
import com.challenge.plugsurfing.dto.MusicBrainzArtistRelation;
import com.challenge.plugsurfing.dto.MusicBrainzArtistReleaseGroup;
import com.challenge.plugsurfing.dto.MusicBrainzArtistResponse;
import com.challenge.plugsurfing.dto.RelationUrl;
import com.challenge.plugsurfing.dto.SitelinkData;
import com.challenge.plugsurfing.dto.WikiEntity;
import com.challenge.plugsurfing.dto.WikidataResponse;
import com.challenge.plugsurfing.dto.WikipediaResponse;
import com.challenge.plugsurfing.exception.DataNotFoundException;
import com.challenge.plugsurfing.external.CoverArtArchiveClient;
import com.challenge.plugsurfing.external.MusicBrainzClient;
import com.challenge.plugsurfing.external.WikidataClient;
import com.challenge.plugsurfing.external.WikipediaClient;

@SpringBootTest
class MusicArtistServiceTest {

  @Autowired
  private MusicArtistService musicArtistService;

  @MockBean
  private MusicBrainzClient musicBrainzClient;

  @MockBean
  private CoverArtArchiveClient coverArtArchiveClient;

  @MockBean
  private WikidataClient wikidataClient;

  @MockBean
  private WikipediaClient wikipediaClient;

  @BeforeEach
  void setUp() throws Exception {

  }

  @Test
  void testGetMusicArtistInformation() {
    final String mbid = "mbid-test";
    final String wikiId = "wikiId-test";
    final String artistTitle = "artistTitle-test";
    final String titleDescription = "test-title-description";
    final String description = "test-description";

    when(musicBrainzClient.getArtistInformationByMBID(mbid)).thenReturn(buildMockMusicBrainzResponse(mbid, wikiId));
    when(wikidataClient.getArtistWikidataInformationByWikiId(wikiId)).thenReturn(getMockedWikiData(wikiId, artistTitle));
    when(wikipediaClient.getArtistWikipediaInformationByTitle(artistTitle))
        .thenReturn(getMockedWidipediaResponse(artistTitle, titleDescription, description));
    when(coverArtArchiveClient.getArtCoverByMBID(mbid)).thenReturn(Optional.of(getMockedArtCover()));

    MusicArtistResponse musicArtistResponse = musicArtistService.getMusicArtistInformation(mbid);

    assertEquals(artistTitle, musicArtistResponse.getArtistTitle());
    assertEquals(titleDescription, musicArtistResponse.getArtistTitleDesc());
    assertEquals(description, musicArtistResponse.getDescription());

  }


  @Test
  void testGetMusicArtistInformationThrowExceptionForInvalidMbid() {
    final String mbid = "mbid-test";

    doThrow(new DataNotFoundException("400 BAD_REQUEST!!! Exception occurs while fetching MusicBrainz data: Invalid mbid.")).when(musicBrainzClient)
        .getArtistInformationByMBID(mbid);

    DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
      musicArtistService.getMusicArtistInformation(mbid);
    });

    String expectedMessage = "400 BAD_REQUEST!!! Exception occurs while fetching MusicBrainz data: Invalid mbid.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

  }

  private MusicBrainzArtistResponse buildMockMusicBrainzResponse(String mbid, String wikiId) {
    MusicBrainzArtistRelation artistRelation = getMockedArtistRelation(wikiId);

    MusicBrainzArtistReleaseGroup artistReleaseGroup = getMockedArtistReleaseGroup(mbid);

    return MusicBrainzArtistResponse.builder()
        .name("test-artist")
        .gender("test-gender")
        .country("test-country")
        .musicBrainzArtistRelations(List.of(artistRelation))
        .musicBrainzArtistReleaseGroups(List.of(artistReleaseGroup))
        .build();
  }

  private MusicBrainzArtistReleaseGroup getMockedArtistReleaseGroup(String mbid) {
    MusicBrainzArtistReleaseGroup artistReleaseGroup =
        MusicBrainzArtistReleaseGroup.builder().mbid(mbid).type("Album").releaseDate("1972-08-04").artCover(getMockedArtCover()).build();
    return artistReleaseGroup;
  }

  private MusicBrainzArtistRelation getMockedArtistRelation(String wikiId) {
    MusicBrainzArtistRelation artistRelation = MusicBrainzArtistRelation.builder()
        .type("wikidata")
        .url(RelationUrl.builder().id(wikiId).resource("https://www.wikidata.org/wiki/" + wikiId).id(UUID.randomUUID().toString()).build())
        .build();
    return artistRelation;
  }

  private WikidataResponse getMockedWikiData(String wikiId, String artistTitle) {
    SitelinkData sitelinkData = SitelinkData.builder().site("enwiki").title(artistTitle).url("https://eml.wikipedia.org/wiki/" + artistTitle).build();
    WikiEntity wikiEntity = WikiEntity.builder().sitelinks(Map.of("enwiki", sitelinkData)).build();
    return WikidataResponse.builder().entities(Map.of(wikiId, wikiEntity)).build();
  }

  private WikipediaResponse getMockedWidipediaResponse(String artistTitle, String titleDescription, String description) {
    return WikipediaResponse.builder().description(titleDescription).extract(description).title(artistTitle).build();
  }

  private ArtCover getMockedArtCover() {
    return ArtCover.builder()
        .images(List.of(Image.builder()
            .image("http://domain-name/release/abc.jpg")
            .thumbnails(Map.of("small", "http://domain-name/release/abc-small.jpg"))
            .build()))
        .build();
  }

}
