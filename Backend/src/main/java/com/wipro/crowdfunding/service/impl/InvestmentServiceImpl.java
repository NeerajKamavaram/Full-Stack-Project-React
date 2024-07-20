package com.wipro.crowdfunding.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.crowdfunding.dto.InvestmentDTO;
import com.wipro.crowdfunding.entity.Investment;
import com.wipro.crowdfunding.entity.Project;
import com.wipro.crowdfunding.exception.ProjectNotFoundException;
import com.wipro.crowdfunding.exception.ResourseNotFoundException;
import com.wipro.crowdfunding.repo.InvestmentRepository;
import com.wipro.crowdfunding.repo.ProjectRepository;
import com.wipro.crowdfunding.service.InvestmentService;

@Service
public class InvestmentServiceImpl implements InvestmentService
{
	@Autowired
	private InvestmentRepository investmentRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public InvestmentDTO makeInvestment(InvestmentDTO investmentDTO) 
	{
		Optional<Project> optionalProject=projectRepository.findById(investmentDTO.getProjectId());
		Project project=optionalProject.get();
		if(investmentDTO.getProjectId()!=project.getId())
		{
			throw new ProjectNotFoundException("Project not found with "+investmentDTO.getProjectId());
		}
		Investment investment=modelMapper.map(investmentDTO,Investment.class);
		Investment madeInvestment=investmentRepository.save(investment);
		InvestmentDTO madeInvestmentDTO=modelMapper.map(madeInvestment, InvestmentDTO.class);
		return madeInvestmentDTO;
		
	}

	@Override
	public InvestmentDTO updateInvestment(Long investmentId, InvestmentDTO investmentDTO)
	{
		Optional<Investment> optionalInvestment=investmentRepository.findById(investmentId);
		if(optionalInvestment.isEmpty())
		{
			throw new ResourseNotFoundException("Investment not found");
		}
		Investment investment=modelMapper.map(investmentDTO, Investment.class);
		Investment updatedInvestment=investmentRepository.save(investment);
		return modelMapper.map(updatedInvestment, InvestmentDTO.class);
		
	}

	@Override
	public boolean deleteInvestment(Long investmentId) 
	{
		Optional<Investment> optionalInvestment=investmentRepository.findById(investmentId);
		if(optionalInvestment.isEmpty())
		{
			throw new ResourseNotFoundException("Investment not found");
		}
		Investment investment=optionalInvestment.get();
		investmentRepository.delete(investment);
		return true;
	}

	@Override
	public InvestmentDTO getInvestmentById(Long investmentId)
	{
		Optional<Investment> optionalInvestment = investmentRepository.findById(investmentId);
		if(optionalInvestment.isEmpty()) 
		{
			throw new ResourseNotFoundException("Investment not found");
		}		
		Investment investment = optionalInvestment.get();	
		
		return modelMapper.map(investment, InvestmentDTO.class);
	}

	@Override
	public List<InvestmentDTO> getInvestmentsByProjectId(Long projectId) 
	{
		List<Investment> investments = investmentRepository.findByProjectId(projectId);
		if(investments.isEmpty())
		{
			throw new ResourseNotFoundException("Investment not found");
		}
		return investments.stream().map((investment)->modelMapper.map(investment, InvestmentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<InvestmentDTO> getInvestmentsByInvestorName(String investorName) 
	{
		List<Investment> investments = investmentRepository.findByInvestorName(investorName);
		if(investments.isEmpty())
		{
			throw new ResourseNotFoundException("Investment not found");
		}
		return investments.stream().map((investment)->modelMapper.map(investment, InvestmentDTO.class))
				.collect(Collectors.toList());
	}
	

}
