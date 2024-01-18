# Test assignment "Filters"

## Overview

The Filter Management Application enables the creation, editing, deletion, and viewing of filters. Its homepage shows currently available filters and features an "Add Filter" button alongside a "Mode" button, which allows users to toggle between modal and non-modal interfaces for editing or creating filter details. The application's backend is developed using Spring Boot and Java, with the frontend implemented in Vue.js.

## Installation

### Without Docker

1. **Clone the Repository**

   Use the following command to clone the repository:

   ```
   git clone https://github.com/39104140296/test-assignment-filters.git
   ```
2. **Backend Setup**
   
   Navigate to the backend directory and run:
   ```
   mvn spring-boot:run
   ```

   This will start the backend server on http://localhost:8080/

3. **Frontend Setup**

   In the frontend directory, install the dependencies:
   ```
   npm install
   ```

   Then start the frontend server:
   ```
   npm run dev
   ```

   Access the application at http://localhost:5173/

### With Docker

1. **Clone the Repository**
   Clone the project repository using:
   
   ```
   git clone https://github.com/39104140296/test-assignment-filters.git
   ```

2. **Run Docker Compose**
   In the project root directory, execute:

   ```
   docker compose up -d
   ```

   This command will build and start the containers. The application will be accessible at http://localhost:5173/

   
    
