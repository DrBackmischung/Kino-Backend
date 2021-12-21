package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.configurationObject.NewsConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.News;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.NewsRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class NewsControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	NewsRepository repo;
	
	@MockBean
	UserRepository userRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<News> jt;
	JacksonTester<NewsConfigurationObject> jtco;
	JacksonTester<Iterable<News>> jti;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    News getNews() {
    	News n = new News(null, null, "News!", "Mathis stinkt", "127.0.0.1", null);
    	n.setId(uuid);
    	return n;
    }
    
    Optional<News> getOptionalNews() {
    	News n = getNews();
    	return Optional.of(n);
    }
    
    User getUser() {
    	User s = new User();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<User> getOptionalUser() {
    	User s = getUser();
    	return Optional.of(s);
    }
    
    @SuppressWarnings("deprecation")
	Iterable<News> getNewsList() {
    	List<News> news = new ArrayList<>();
    	news.add(new News(new Date(2021, 12, 21), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2021, 12, 22), new Time(3), "Noch balder Weihnachten", "Der kleine Mathis freut sich immer noch auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2021, 10, 24), new Time(3), "Spooky Time", "Der kleine Mathis freut sich auf Schokolade!", "Link", null));
    	news.add(new News(new Date(2020, 12, 24), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2020, 12, 1), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	return news;
    }
    
    @SuppressWarnings("deprecation")
	Iterable<News> getDateNewsList() {
    	List<News> news = new ArrayList<>();
    	news.add(new News(new Date(2021, 12, 21), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	return news;
    }
    
    @SuppressWarnings("deprecation")
	Iterable<News> getMonthNewsList() {
    	List<News> news = new ArrayList<>();
    	news.add(new News(new Date(2021, 12, 21), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2021, 12, 22), new Time(3), "Noch balder Weihnachten", "Der kleine Mathis freut sich immer noch auf Weihnachten!", "Link", null));
    	return news;
    }
    
    @SuppressWarnings("deprecation")
	Iterable<News> getYearNewsList() {
    	List<News> news = new ArrayList<>();
    	news.add(new News(new Date(2021, 12, 21), new Time(3), "Bald Weihnachten", "Der kleine Mathis freut sich auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2021, 12, 22), new Time(3), "Noch balder Weihnachten", "Der kleine Mathis freut sich immer noch auf Weihnachten!", "Link", null));
    	news.add(new News(new Date(2021, 10, 24), new Time(3), "Spooky Time", "Der kleine Mathis freut sich auf Schokolade!", "Link", null));
    	return news;
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<News>());
        mvc.perform(get("/news/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalNews());
        MockHttpServletResponse response = mvc.perform(get("/news/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getNews()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/news/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetByDate() throws Exception {
        when(repo.findAll()).thenReturn(getNewsList());
        MockHttpServletResponse response = mvc.perform(get("/news/q/2021/12/21")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
    }
    
    @Test
    void testGetByMonth() throws Exception {
        when(repo.findAll()).thenReturn(getNewsList());
        MockHttpServletResponse response = mvc.perform(get("/news/q/2021/12")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
    }
    
    @Test
    void testGetByYear() throws Exception {
        when(repo.findAll()).thenReturn(getNewsList());
        MockHttpServletResponse response = mvc.perform(get("/news/q/2021")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
    }

    @Test
    void testPut() throws Exception{

        mvc.perform(
            put("/news/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getNews()).getJson()))
        		.andExpect(status().isCreated());
        
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(
            put("/news/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new NewsConfigurationObject(new Date(2), new Time(3), "Head", "Body", "Link", uuid)).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalNews());
        mvc.perform(
            delete("/news/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{

        mvc.perform(
            delete("/news/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
