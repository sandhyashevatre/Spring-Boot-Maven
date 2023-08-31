package com.example.learningcycles.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.learningcycles.entity.Cycles;
import com.example.learningcycles.repository.CycleRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cycle")
public class CycleController {
	
	@Autowired
	private Optional<Cycles> cycle;
	@Autowired
	private CycleRepository cycleRepo;
	
	@GetMapping
	public void getResp(HttpServletResponse resp) throws IOException {
		resp.getWriter().println("heyy there!!");
	}
	
	@GetMapping("/listAllAvailableCycles")
	public String getCycles(Model model) {
		List<Cycles> cycleList = new ArrayList<>();
		cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
		model.addAttribute("cycleList",cycleList);
		return "cyclelist";
	}
	@PostMapping("listAllAvailableCycles")
	public String takeCycle(@RequestParam(name="taken") Integer id){
		cycle = cycleRepo.findById(id);
		cycleRepo.deleteById(id);
		return "redirect:/cycle/listAllAvailableCycles";	
	}
	
	@GetMapping("borrow")
	public void getResponse(HttpServletResponse resp) throws IOException {
		resp.getWriter().append(cycle.get().getName());
	}
	@GetMapping("/return")
    public String putResponse() {
        cycleRepo.save(cycle.get());
        return "redirect:/cycle/listAllAvailableCycles";
    }
}


