import { Formik, Form, Field, ErrorMessage } from 'formik';
import React, { useEffect, useState } from 'react';
import * as Yup from 'yup';
import crowdfundingService from '../services/CrowdFunding.service';

const InvestmentForm = ({investment}) => {

    // const [investments, setInvestments] = useState([]);
    const [projects, setProjects] = useState([]);
    
    const investmentSchema = Yup.object().shape({
        amount: Yup.number().required("Amount is required").positive("Amount must be positive"),
        investorName: Yup.string().required("Investor name is required"),
        projectId: Yup.string().required("Project is required")
    });

    useEffect(() => {
        crowdfundingService.getAllProjects()
            .then(resp => setProjects(resp.data))
            .catch(error => {
                console.error("Error fetching projects:", error);
                setProjects([]);
            });
    }, []);

    const handleSubmit = (values, { setSubmitting }) => {
        if (investment) {
            crowdfundingService.updateInvestment(investment.id, values)
                .then(resp => {
                    alert("Investment updated");
                })
                .catch(error => {
                    alert("Failed to update investment: " + error.message);
                });
        } 
        else 
        {
            crowdfundingService.createInvestment(values)
                .then(resp => {
                    alert("Investment created");
                })
                .catch(error => {
                    alert("Failed to create investment: " + error.message);
                });
        }
        setSubmitting(false);
    };
    


    return (
        <div>
            <h2>{investment?'Edit Investment' : 'create investment'}</h2>

            <Formik
                initialValues={{
                    id:investment?investment.id:1,
                    amount:investment?investment.amount:"",
                    investorName:investment?investment.investorName:"",
                    projectId:investment?investment.projectId:""
                }}

                validationSchema={investmentSchema}
                onSubmit={handleSubmit}
                

            >

                {({ isSubmitting,isValid,dirty }) => <Form>
                    <label htmlFor="investmentAmount">Amount:</label>
                    <Field id="investmentAmount" type="text" name="amount" placeholder="Investment Amount"/>
                    <ErrorMessage name="amount" component="div" />

                    <label htmlFor="investorName">Investor Name:</label>
                    <Field id="investorName" type="text" name="investorName" placeholder="Investor Name"/>
                    <ErrorMessage name="investorName" component="div" />

                    <label htmlFor="projectId">Project:</label>
                    <Field id="projectId" as="select" name="projectId">
                        <option value="">Select a project</option>
                        {projects.map(p => (
                            <option key={p.id} value={p.id}>{p.name}</option>
                        ))}
                    </Field>
                    <ErrorMessage name="projectId" component="div" />

                    <button type="submit" disabled={!(dirty && isValid)}>
                    {investment?'update Investment':'create Investment'}
                    </button>
                </Form>
                }

            </Formik>



        </div>
    );
};

export default InvestmentForm;