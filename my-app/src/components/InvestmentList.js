import { Field, Form, Formik } from 'formik';
import React, { useState } from 'react';
import crowdfundingService from '../services/CrowdFunding.service';
import InvestmentForm from './InvestmentForm';

const InvestmentList = () => {

    const[investments,setInvestments]=useState([]);
    const [editInvestment, setEditInvestment] = useState(null);

    const handleEdit=(investment)=>{
        setEditInvestment(investment)
    }
    const handleDelete = (investmentId) => {
        crowdfundingService.deleteInvestment(investmentId)
            .then(resp => {
                alert("Investment deleted successfully");
            })
            .catch(error => {
                alert("Failed to delete investment: " + error.message);
            });
    }
    

    return (
        <div>
            {
                editInvestment!==null && (
                    <InvestmentForm investment={editInvestment} />
                )
            }
            {
                editInvestment===null && (
                    <InvestmentForm investment={editInvestment}/>
                )
            }
            <h3>Search Investments by Investor Name</h3>

            <Formik
                initialValues={{
                    investorName:""
                }}

            onSubmit={values=>{
                crowdfundingService.getInvestmentsByInvestorName(values.investorName)
                .then(resp => {
                    setInvestments(resp.data)
                })
                .catch(error => {
                    alert("Failed to search investment by investor name: " + values.investorName);
                });
            }}
            
            
            >

            {({isSubmitting})=><Form>

                <Field id="enterInvestorName" type="text" name="investorName" placeholder="Enter investor name"/>
                <button type="submit">search</button>
                </Form>}

            </Formik>
            {
                investments.length>0 &&
                investments.map(inv=><div key={inv.id}>

                    <p><b>Amount:</b>{' '}{inv.amount}
                      <b style={{ marginLeft: '15px' }}>Investor Name:</b>{' '}{inv.investorName}
                        <b style={{ marginLeft: '15px' }}>Project ID:</b>{' '}{inv.projectId}
                        <button style={{ marginLeft: '15px' }} onClick={()=>handleEdit(inv)}>Edit</button>
                        <button style={{ marginLeft: '10px' }} onClick={()=>handleDelete(inv.id)}>Delete</button></p>
                    
                    </div>)
            }
        </div>
    );
};

export default InvestmentList;