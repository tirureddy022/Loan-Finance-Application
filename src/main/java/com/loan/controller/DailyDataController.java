package com.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loan.binding.DailyEntryFormBinding;
import com.loan.service.DailyDetailsService;

@Controller
public class DailyDataController {

	@Autowired
	private DailyDetailsService service;
	

	@GetMapping("/index")
	public String indexPage(Model model) {
		model.addAttribute("personData", new DailyEntryFormBinding());
		model.addAttribute("processeddata", service.getStastics());
		model.addAttribute("listOfRecords", service.getAllFromMap().entrySet());
		return "index";
	}

	@PostMapping("/savetodb")
	public String saveToDataBase() {
		service.saveToDB();
		return "redirect:index";
	}

	@PostMapping("/save")
	public String saveToCollection(DailyEntryFormBinding details, Model model) {

		DailyEntryFormBinding saveToMap = service.saveToMap(details);
		DailyEntryFormBinding obj = new DailyEntryFormBinding();
		obj.setDate(saveToMap.getDate());

		model.addAttribute("personData", obj);
		model.addAttribute("processeddata", service.getStastics());
		model.addAttribute("listOfRecords", service.getAllFromMap().entrySet());
		return "index";
	}

	@GetMapping("/delete")
	public String deleteFromCollection(@RequestParam("index") Integer index) {
		service.delete(index);
		return "redirect:index";
	}

	@GetMapping("/edit")
	public String editData(@RequestParam("index") Integer num, Model model) {
		DailyEntryFormBinding update = service.delete(num);
		model.addAttribute("loanData", update);
		return "edit";
	}

	@GetMapping("/logout")
	public String logOut(Model model) {

		return "redirect:index";
	}

}
