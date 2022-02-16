package com.example.adproject.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.adproject.helper.BlogEntry;
import com.example.adproject.helper.EntriesDataTable;
import com.example.adproject.helper.RecResultJson;
import com.example.adproject.helper.SearchResult;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.UserService;

@RestController
@RequestMapping("/api/recommend")
public class RecommenderAPI {
	
	@Autowired
	UserService uService;

	@Autowired
	MealEntryRepo mrepo;

	@Autowired
	MealEntryService mService;
	
	@RequestMapping(value = "/postStringData/{uid}/{input}/{feeling}/{track}", method = {RequestMethod.POST, RequestMethod.GET})
	public RecResultJson postQuery(@PathVariable("uid") Integer userId, @PathVariable("input") String input,
			@PathVariable("feeling") String feeling, @PathVariable("track") String track) {
		String url = "http://localhost:5000/enterquery/" 
			+ userId + "/" + input + "/" + feeling + "/" + track;
		RestTemplate restTemplate = new RestTemplate();
		SearchResult results = new SearchResult();
		results = restTemplate.getForObject(url, SearchResult.class);
		
		MealEntry[] resultArray = new MealEntry[] {
				mService.findMealEntryById(results.getRes0()),
				mService.findMealEntryById(results.getRes1()),
				mService.findMealEntryById(results.getRes2()),
				mService.findMealEntryById(results.getRes3()),
				mService.findMealEntryById(results.getRes4()),
		};
		
		RecResultJson r = new RecResultJson(resultArray, results.getGoodResult());
		
		return r;
	}

	//pass mealEntryTable to Flask
	@RequestMapping(value = "/passData/{uid}", method = {RequestMethod.POST, RequestMethod.GET})
	public EntriesDataTable passDataToFlask(@PathVariable("uid") String userId) {
		System.out.println("userId: " + userId);
		User user = uService.findUser(Integer.parseInt(userId));
		List<MealEntry> entries = mService.findMealEntryByUser(user);
		//to implement include friend's posts
		//...
		
		Integer size = entries.size();
		System.out.println(size);
		EntriesDataTable data = new EntriesDataTable();
		List<Integer> ids = new ArrayList<Integer>();
		List<String> titles = new ArrayList<String>();
		List<String> descriptions = new ArrayList<String>();
		List<String> feelings = new ArrayList<String>();
		List<Integer> track_scores = new ArrayList<Integer>();
		for (MealEntry m : entries) {
			ids.add(m.getId());
			titles.add(m.getTitle());
			descriptions.add(m.getDescription());
			feelings.add(m.getFeeling().toString());
			track_scores.add(m.getTrackScore());
		}
		data.setId(ids.toArray(new Integer[size]));
		data.setTitle(titles.toArray(new String[size]));
		data.setDescription(descriptions.toArray(new String[size]));
		data.setFeeling(feelings.toArray(new String[size]));
		data.setTrack_score(track_scores.toArray(new Integer[size]));
		return data;
	}
	
	@RequestMapping(value = "/getEntryCount/{uid}", method = RequestMethod.GET)
	public Integer getEntryCount(@PathVariable("uid") Integer userId) {
		Integer count = mService.findEntryByAuthor(userId).size();
		System.out.println(count);
		return count;
	}
	
	@GetMapping(value ="/getEntryPic", produces = MediaType.IMAGE_JPEG_VALUE, params = {"fileName"})
	public @ResponseBody byte[] getEntryPic(@RequestParam String fileName) throws IOException {
		String path = "upload/" + fileName;
		System.out.println(path);
		
		File file = new File(path);
		byte[] fileContent = Files.readAllBytes(file.toPath());
		return fileContent;
		
//		InputStream in = getClass()
//				.getResourceAsStream(path);
//		return IOUtils.toByteArray(in);
	}
}
