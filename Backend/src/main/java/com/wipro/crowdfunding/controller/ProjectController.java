package com.wipro.crowdfunding.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.crowdfunding.dto.ProjectDTO;
import com.wipro.crowdfunding.service.ProjectService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/projects")
public class ProjectController
{
	@Autowired
	private ProjectService projectService;
	
	@PostMapping
	public ResponseEntity<ProjectDTO>  makeProject(@Valid @RequestBody ProjectDTO projectDTO) 
	{
		projectService.createProject(projectDTO);
		ResponseEntity<ProjectDTO> re = new ResponseEntity<>(projectDTO,HttpStatus.CREATED);		
		return re;
	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<ProjectDTO> updateProject(@PathVariable("projectId") Long projectId,
			@Valid  @RequestBody ProjectDTO projectDTO)
	{
		ProjectDTO updatedProjectDTO = projectService.updateProject(projectId, projectDTO);
	    ResponseEntity<ProjectDTO> re = new ResponseEntity<>(updatedProjectDTO,HttpStatus.OK);
	    return re;
	   
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable("projectId") Long projectId)
	{
		projectService.deleteProject(projectId);
		ResponseEntity<Void> re=new ResponseEntity<>(HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<ProjectDTO> fetchProject( @PathVariable("projectId") Long projectId) 
	{
		ProjectDTO projectDTO = projectService.getProjectById(projectId);
		ResponseEntity<ProjectDTO> re = new ResponseEntity<>(projectDTO,HttpStatus.OK);	
		return re;
	}	
	
	@GetMapping
	public List<ProjectDTO> fetchAllProjects() 
	{		
		List<ProjectDTO> customers = projectService.getAllProjects();
		return customers;
	}
 
}
