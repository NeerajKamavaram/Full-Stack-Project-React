package com.wipro.crowdfunding.service.impl;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.crowdfunding.dto.InvestmentDTO;
import com.wipro.crowdfunding.dto.ProjectDTO;
import com.wipro.crowdfunding.entity.Project;
import com.wipro.crowdfunding.exception.ResourseNotFoundException;
import com.wipro.crowdfunding.repo.ProjectRepository;
import com.wipro.crowdfunding.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProjectDTO createProject(ProjectDTO projectDTO) 
	{
		List<InvestmentDTO> investmentsDTO=projectDTO.getInvestments();
		
//		List<Investment> investments=investmentsDTO.stream().map((investment) -> modelMapper.map(investment, Investment.class))
//		.collect(Collectors.toList());
		Project project=modelMapper.map(projectDTO, Project.class);
		
		//project.setInvestments(investments);
		Project newProject=projectRepository.save(project);
		return modelMapper.map(newProject, ProjectDTO.class);
		
		
	}

	@Override
	public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO)
	{
		Optional<Project> optionalProject=projectRepository.findById(projectId);
		if(optionalProject.isEmpty())
		{
			throw new ResourseNotFoundException("Project not found");
		}
		Project existingProject=optionalProject.get();
		
		existingProject.setName(projectDTO.getName());
	    existingProject.setDescription(projectDTO.getDescription());
	    existingProject.setGoalAmount(projectDTO.getGoalAmount());
	    existingProject.setAmountRaised(projectDTO.getAmountRaised());
	    
		modelMapper.map(projectDTO, existingProject);
		Project updatedProject=projectRepository.save(existingProject);
		return modelMapper.map(updatedProject, ProjectDTO.class);
	}

	@Override
	public boolean deleteProject(Long projectId)
	{
		Optional<Project> optionalProject=projectRepository.findById(projectId);
		if(optionalProject.isEmpty())
		{
			throw new ResourseNotFoundException("Project not found");
		}
		Project  project=optionalProject.get();
		projectRepository.delete(project);
		return true;
	}

	@Override
	public ProjectDTO getProjectById(Long projectId) 
	{
		Optional<Project> optionalProject=projectRepository.findById(projectId);
		if(optionalProject.isEmpty())
		{
			throw new ResourseNotFoundException("Project not found");
		}		
		Project project = optionalProject.get();	
		
		return modelMapper.map(project, ProjectDTO.class);
	}

	@Override
	public List<ProjectDTO> getAllProjects() 
	{
		List<Project> projects=projectRepository.findAll();
		
		return projects.stream().map((project)->modelMapper.map(project, ProjectDTO.class))
				.collect(Collectors.toList());
	}
	

}
