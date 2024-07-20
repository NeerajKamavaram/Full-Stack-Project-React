import axios from 'axios';
const BACKEND_URL = "http://localhost:8081/crowdfunding/api";

const crowdfundingService = {
    getAllProjects: async () => {
        // write your logic here
        return axios.get(BACKEND_URL+"/projects");
    },

    getProjectById: async (projectId) => {
        // write your logic here
        return null
    },

    createProject: async (projectData) => {
        // write your logic here
        return axios.post(BACKEND_URL+"/projects",projectData)
    },

    updateProject: async (projectId, projectData) => {
        // write your logic here
        return axios.put(BACKEND_URL+"/projects/"+projectId,projectData)
    },

    deleteProject: async (projectId) => {
        // write your logic here
        return axios.delete(BACKEND_URL+"/projects/"+projectId);
    },

    getAllInvestmentsForProject: async (projectId) => {
        // write your logic here
        return axios.get(BACKEND_URL+"/investments/project/"+projectId)
    },

    getInvestmentById: async (investmentId) => {
        // write your logic here
        return null
    },

    createInvestment: async (investmentData) => {
        // write your logic here
        return axios.post(BACKEND_URL+"/investments",investmentData);
    },

    updateInvestment: async (investmentId, investmentData) => {
        // write your logic here
        return axios.put(BACKEND_URL+"/investments/"+investmentId,investmentData)
    },

    deleteInvestment: async (investmentId) => {
        // write your logic here
        return axios.delete(BACKEND_URL+"/investments/"+investmentId)
    },

    getInvestmentsByProjectId: async (projectId) => {
        // write your logic here
        return null
    },

    getInvestmentsByInvestorName: async (investorName) => {
        // write your logic here
        return axios.get(BACKEND_URL+"/investments/investor/"+investorName);
    },
};

export default crowdfundingService;