# Three-Tier Web Application

## Overview

This project is a three-tier web application consisting of a frontend, backend, and storage layer. The architecture is designed to separate concerns, allowing for easier maintenance and scalability.

## Project Structure

```
three-tier-web-app
├── backend          # Contains the backend application
│   ├── src         # Source files for the backend
│   │   ├── app.ts  # Entry point of the backend application
│   │   ├── controllers # Contains request handling logic
│   │   ├── routes   # Defines application routes
│   │   └── models   # Data models for the application
│   ├── package.json # Backend dependencies and scripts
│   ├── tsconfig.json # TypeScript configuration for the backend
│   └── README.md    # Documentation for the backend
├── frontend         # Contains the frontend application
│   ├── src         # Source files for the frontend
│   │   ├── App.tsx  # Main component of the frontend
│   │   ├── components # Reusable components
│   │   └── pages    # Application pages
│   ├── package.json # Frontend dependencies and scripts
│   ├── tsconfig.json # TypeScript configuration for the frontend
│   └── README.md    # Documentation for the frontend
├── storage          # Contains the storage layer
│   ├── schema      # SQL schema for the database
│   │   └── schema.sql # Database schema definition
│   └── README.md    # Documentation for the storage component
└── README.md        # Overall documentation for the application
```

## Getting Started

To get started with the three-tier web application, follow the instructions below for each component:

### Backend

1. Navigate to the `backend` directory.
2. Install dependencies using `npm install`.
3. Start the backend server with `npm start`.

### Frontend

1. Navigate to the `frontend` directory.
2. Install dependencies using `npm install`.
3. Start the frontend application with `npm start`.

### Storage

1. Navigate to the `storage` directory.
2. Execute the SQL schema in your database to set up the required tables.

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.