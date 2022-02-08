package com.example.adproject.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.Goal;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.GoalService;







@Controller
@RequestMapping(value = "/user")

	
public class userController {
	

	@Autowired
	private com.example.adproject.service.UserService uService;
	
	@Autowired
    private GoalService gService;
	

	@Autowired
    UserRepo urepo;


	@Autowired
    GoalRepo grepo;

	@Autowired
	MealEntryRepo mRepo;


	
	@RequestMapping(value = "/myProfile/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(Principal principal) {
		ModelAndView mav = new ModelAndView("staff-profile-edit");
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User user = uService.findUser(userId);
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping(value = "/myProfile/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editProfile(@ModelAttribute @Validated User user, @RequestParam("fileImage") MultipartFile multipartFile, 
			Principal principal) throws IOException  {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		
		// User's profilepic
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename()); 
				user.setProfilePic(fileName); 
		
				
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		User updateUser = urepo.findById(userId).get();
		updateUser.setName(user.getName());
		updateUser.setDateOfBirth(user.getDateOfBirth());
		updateUser.setHeight(user.getHeight());
		updateUser.setWeight(user.getWeight());
		updateUser.setProfilePic(fileName);
		urepo.saveAndFlush(updateUser);
		String uploadDir = "src/main/resources/static/images/";
//		String archiveDir= "src/main/resources/static/images/";
		Path uploadPath = Paths.get(uploadDir); 
//		Path photoPath = Paths.get(archiveDir);
		// Saving user's profile pic into directory 
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				try {
					InputStream inputStream = multipartFile.getInputStream(); 
					Path filePath = uploadPath.resolve(fileName); 
//					Path photoPath1 = photoPath.resolve(fileName); 
//					Files.copy(inputStream, photoPath1, StandardCopyOption.REPLACE_EXISTING);
					Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING);
//					Files.copy(filePath, photoPath1, StandardCopyOption.REPLACE_EXISTING);
					
				} catch (IOException e) {
					throw new IOException("Could not save uploaded file: " + fileName); 
				}
		
		String message = "User was successfully updated.";
		System.out.println(message);
		
		mav.setViewName("redirect:/user/myProfile");
		return mav;
	}
	

	
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView ViewMyProfile(@ModelAttribute User user,
			Principal principal) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
//		UserSession usession = (UserSession) session.getAttribute("usession");
		ModelAndView mav = new ModelAndView("staff-course-myProfile");
		user = uService.findUser(userId);
		List<Goal> completedGoal = grepo.findCompletedGoals(userId);
		
		//Calculate BMI
		double height =user.getHeight();
		double weight = user.getWeight();
		double bmiFloat = weight/(height*height/10000);
		String bmi = String.format("%.1f",bmiFloat);
		
		mav.addObject("user", user);
		mav.addObject("bmi", bmi);
		mav.addObject("completedGoal", completedGoal);
		return mav;
	}
	//View current goal progress
	@RequestMapping(value = "/goals")
	public String goals(Model model, Principal principal) {
		
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		Goal currentgoal = grepo.findCurrentGoal(userId);
		long countOnT = mRepo.findEntryByAuthor(userId).stream()
				.filter(x->x.getTrackScore()==1)
				.count();
		long countOffT = mRepo.findEntryByAuthor(userId).stream()
				.filter(x->x.getTrackScore() == 0)
				.count();
		
		long totalmeals = countOnT + countOffT;
		double percentCount = (countOnT*100/totalmeals) ;
		String percentCount1 = String.format("%.1f",percentCount);
		
		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		model.addAttribute("totalMeals", totalmeals);
		model.addAttribute("percentCount", percentCount1);
		model.addAttribute("goal", currentgoal);
		return "goal-progress";
	}


	//Save&Continue end of a goal
	@RequestMapping(value = "/goals/continue")
	public String Continue(Principal principal, Model model) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		Goal goaltoEnd = grepo.findCurrentGoal(userId);
		gService.cancelGoal(goaltoEnd);
		String msg = "Leave was successfully cancelled.";
		System.out.println(msg);	
		return "index";
		
		
	}
	
}
