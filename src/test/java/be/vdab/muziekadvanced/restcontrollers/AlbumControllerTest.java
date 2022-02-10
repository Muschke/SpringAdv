package be.vdab.muziekadvanced.restcontrollers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/insertArtiest.sql", "/insertAlbum.sql"})
class AlbumControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final MockMvc mvc;

    public AlbumControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    private long idVanTestAlbum() {
        return jdbcTemplate.queryForObject(
                "select id from albums where naam = 'testalbum'", Long.class);
    }
    @Test
    void onbestaandeAlbumLezen() throws Exception {
        mvc.perform(get("/albums/{id}", -1))
                .andExpect(status().isNotFound());
    }
    @Test
    void albumLezen() throws Exception{
        var id = idVanTestAlbum();
        mvc.perform(get("/albums/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("albumNaam").value("testalbum"));


        //Je geeft aan jsonPath een JSONPath expressie mee. Je zoekt daarmee data in JSON data.
           //     Je zoekt hier de waarde van het attribuut albumnaam.
        //Die moet gelijk zijn aan waarde testalbum
    }
}