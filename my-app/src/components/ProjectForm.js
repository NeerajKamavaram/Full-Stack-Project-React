import React, { useState, useEffect } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import crowdfundingService from '../services/CrowdFunding.service';
import * as Yup from 'yup';

const ProjectForm = ({ project,onFormSubmit }) => {


   

    const projectSchema = Yup.object().shape({
        name: Yup.string().required("project name is required"),
        description: Yup.string().required("project description is required"),
        goalAmount: Yup.number().required("goal amount is required"),
        amountRaised: Yup.number().required("amount raised is required")
    })

   

    const handleSubmit = (values) => {
        if (project) {
            crowdfundingService.updateProject(project.id, values)
            .then(resp => {
                onFormSubmit();
            })
            .catch(error => {
                alert("Failed to update project: " + error.message);
            })
        }
        else {
            crowdfundingService.createProject(values)
            .then(resp => {
                onFormSubmit();
            })
            .catch(error => {
                alert("Failed to create project: " + error.message);
            })
        }
    }



    return (
        <div>

            <h2>{project ? 'Edit Project' : 'Manage Projects'}</h2>

            <Formik
                initialValues={
                    {
                        id: project ? project.id : 0,
                        name: project ? project.name : "",
                        description: project ? project.description : "",
                        goalAmount: project ? project.goalAmount : "",
                        amountRaised: project ? project.amountRaised : "",
                    }
                }
                validationSchema={projectSchema}
                onSubmit={handleSubmit}

            >

                {({ isSubmitting,isValid,dirty}) => <Form>
                    <label htmlFor="name">Project Name:</label>
                    <Field id="name" type="text" name="name" placeholder="Project Name" />
                    <ErrorMessage name="name" component="div" />

                    <label htmlFor="description">Project Description:</label>
                    <Field id="description" as="textarea" type="text" name="description" placeholder="Project Description" />
                    <ErrorMessage name="description" component="div" />

                    <label htmlFor="goalAmount">Goal Amount:</label>
                    <Field id="goalAmount" type="text" name="goalAmount" placeholder="Goal Amount" />
                    <ErrorMessage name="goalAmount" component="div" />

                    <label htmlFor="amountRaised">Amount Raised:</label>
                    <Field id="amountRaised" type="text" name="amountRaised" placeholder="Amount Raised" />
                    <ErrorMessage name="amountRaised" component="div" />

                    <button type="submit" disabled={isSubmitting || !(dirty && isValid)}>
                        {project ? 'update project' : 'create project'}
                    </button>
                </Form>

                }

            </Formik>
        </div>
    );
};

export default ProjectForm;