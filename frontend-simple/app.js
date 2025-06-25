// API endpoint
const API_URL = 'http://localhost:8080/api/invoices';

// Function to load all invoices
async function loadInvoices() {
    try {
        const response = await fetch(API_URL);
        const data = await response.json();
        displayInvoices(data.content); // Spring Boot returns paginated data
    } catch (error) {
        console.error('Error loading invoices:', error);
        alert('Error loading invoices');
    }
}

// Function to display invoices in the table
function displayInvoices(invoices) {
    const tableBody = document.getElementById('invoicesTableBody');
    tableBody.innerHTML = '';

    invoices.forEach(invoice => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${invoice.invoiceNumber}</td>
            <td>${new Date(invoice.invoiceDate).toLocaleDateString()}</td>
            <td>$${invoice.totalAmount.toFixed(2)}</td>
            <td>
                <button onclick="deleteInvoice(${invoice.id})" class="delete-btn">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Function to create a new invoice
async function createInvoice(invoiceData) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(invoiceData)
        });

        if (!response.ok) {
            throw new Error('Failed to create invoice');
        }

        loadInvoices(); // Reload the table
    } catch (error) {
        console.error('Error creating invoice:', error);
        alert('Error creating invoice');
    }
}

// Function to delete an invoice
async function deleteInvoice(id) {
    if (!confirm('Are you sure you want to delete this invoice?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete invoice');
        }

        loadInvoices(); // Reload the table
    } catch (error) {
        console.error('Error deleting invoice:', error);
        alert('Error deleting invoice');
    }
}

// Handle form submission
document.getElementById('invoiceForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const invoiceData = {
        invoiceNumber: document.getElementById('invoiceNumber').value,
        invoiceDate: document.getElementById('invoiceDate').value,
        totalAmount: parseFloat(document.getElementById('totalAmount').value)
    };

    createInvoice(invoiceData);
    this.reset(); // Reset form after submission
});

// Load invoices when page loads
loadInvoices();
