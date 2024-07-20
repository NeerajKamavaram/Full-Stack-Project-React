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

import com.wipro.crowdfunding.dto.InvestmentDTO;
import com.wipro.crowdfunding.service.InvestmentService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/investments")
public class InvestmentController 
{
	@Autowired
	private InvestmentService investmentService;
	
	@PostMapping
	public ResponseEntity<InvestmentDTO>  makeInvestment(@Valid @RequestBody InvestmentDTO investmentDTO) 
	{
		investmentService.makeInvestment(investmentDTO);
		ResponseEntity<InvestmentDTO> re = new ResponseEntity<>(investmentDTO,HttpStatus.CREATED);		
		return re;
	}
	
	@DeleteMapping("/{investmentId}")
	public ResponseEntity<Void> deleteInvestment(@PathVariable("investmentId") Long investmentId)
	{
		investmentService.deleteInvestment(investmentId);
		ResponseEntity<Void> re=new ResponseEntity<>(HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/{investmentId}")
	public ResponseEntity<InvestmentDTO> fetchCustomerDetails(@PathVariable("investmentId") Long investmentId) 
	{
		InvestmentDTO investmentDTO = investmentService.getInvestmentById(investmentId);
		ResponseEntity<InvestmentDTO> re = new ResponseEntity<>(investmentDTO,HttpStatus.OK);	
		return re;
	}	
	
	@PutMapping("/{investmentId}")
	public ResponseEntity<InvestmentDTO> updateInvestment(@PathVariable("investmentId") Long investmentId,
			@Valid @RequestBody InvestmentDTO investmentDTO)
	{
	    InvestmentDTO updatedInvestmentDTO = investmentService.updateInvestment(investmentId, investmentDTO);
	    ResponseEntity<InvestmentDTO> re = new ResponseEntity<>(updatedInvestmentDTO,HttpStatus.OK);
	    return re;
	   
	}
	
	@GetMapping("/project/{projectId}")
	public List<InvestmentDTO> getInvestmentsByProjectId( @PathVariable("projectId") Long projectId) 
	{		
		List<InvestmentDTO> investments = investmentService.getInvestmentsByProjectId(projectId);
		return investments;
	}	
	
	@GetMapping("/investor/{investerName}")
	public List<InvestmentDTO> getInvestmentsByProjectId( @PathVariable("investerName") String investerName) 
	{		
		List<InvestmentDTO> investments = investmentService.getInvestmentsByInvestorName(investerName);
		return investments;
	}	

}
