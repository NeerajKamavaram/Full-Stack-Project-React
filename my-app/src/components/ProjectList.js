import React, { useEffect, useState } from 'react';
import crowdfundingService from '../services/CrowdFunding.service';
import ProjectForm from './ProjectForm';

const ProjectList = () => {

    const [projects, setProjects] = useState([]);
    const [editProject, setEditProject] = useState(null);
    const [investments, setInvestments] = useState([]);
    const [selectedProject, setSelectedProject] = useState(null);

   

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = () => {

        crowdfundingService.getAllProjects()
            .then(resp => {
                setProjects(resp.data);
            })
            .catch(error => {
                console.error('Error fetching projects:', error.message);
            });
    };



    const handleDelete = (id) => {
        crowdfundingService.deleteProject(id)
            .then(resp => {
                fetchData(); 
            })
            .catch(error => {
                console.error('Error deleting project:', error);
            });
    };

    const handleEdit = (project) => {
        setEditProject(project);
    }

    const handleFormSubmit = () => {
        fetchData(); 
        setEditProject(null);
    };

    const handleInvestments = (projectId) => {
        crowdfundingService.getAllInvestmentsForProject(projectId)
            .then(resp => {
                setInvestments(resp.data);
                setSelectedProject(projectId);
            })
            .catch(error => {
                console.error('Error fetching investments:', error);
            });
    };


    return (
        <div>
            {
                editProject !== null && (
                    editProject && (
                        <ProjectForm project={editProject} onFormSubmit={handleFormSubmit}/>
                    )
                )}

            {editProject === null && (
                <ProjectForm onFormSubmit={handleFormSubmit}/>
            )}


            <h3>Project List</h3>
            {projects.length > 0 ? (
                projects.map(p => (
                    <div key={p.id}>
                        <p style={{ margin: '1px 0' }}><b>Name:</b> {p.name}</p>
                        <p style={{ margin: '1px 0' }}><b>Description:</b> {p.description}</p>
                        <p style={{ margin: '1px 0' }}><b>Goal Amount:</b> {p.goalAmount}</p>
                        <p style={{ margin: '1px 0' }}><b>Amount Raised:</b> {p.amountRaised}</p>
                        <button onClick={() => handleEdit(p)} style={{ marginRight: '10px' }}>Edit</button>
                        <button onClick={() => handleDelete(p.id)} style={{ marginRight: '10px' }}>Delete</button>
                        <button onClick={() => handleInvestments(p.id)}>Get all investments</button>

                        {selectedProject === p.id && (
                            <div>
                                <h3>Investments for {p.name}</h3>
                                {investments.length > 0 ? (
                                    investments.map(inv => (
                                        <div key={inv.id}>
                                            <p>Amount: {inv.amount}</p>
                                            <p>Investor Name: {inv.investorName}</p>
                                        </div>
                                    ))
                                ) : (
                                    <p>No investments found</p>
                                )}
                            </div>
                        )}
                    </div>
                ))
            ) : (
                <p>No projects found</p>
            )}
        </div>
    );
};

export default ProjectList;