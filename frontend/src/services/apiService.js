import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const axiosInstance = axios.create({
  baseURL: API_BASE_URL
})

export const getAllFilters = async () => {
  try {
    const response = await axiosInstance.get('/filters')
    return response.data
  } catch (error) {
    console.error('Error fetching filters:', error)
    throw error
  }
}
