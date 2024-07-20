package com.wipro.crowdfunding.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.crowdfunding.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>
{

}
