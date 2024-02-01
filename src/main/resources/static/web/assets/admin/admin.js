const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            originalProducts: [],
            products: [],
            error: '',
            categories: [],
            category: '',
            sortByPrice: false,
            minPrice: '',
            maxPrice: null,
            sortByStock: false,
            searchQuery: '',
        };
    },
    created() {
        this.loadData();
    },

    methods: {
        loadData() {
            axios.get("/api/products")
                .then(response => {
                    this.originalProducts = response.data;
                    this.applyFilters();
                    console.log(this.products);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        applyFilters() {
            let filteredProducts = [...this.originalProducts];
        
            if (this.category && this.category !== 'ALL') {
                filteredProducts = filteredProducts.filter(product => product.category == this.category);
            }
        
            if (this.sortByPrice) {
                filteredProducts = filteredProducts.filter(product => {
                    const price = product.price;
                    const isInRange = (this.minPrice === '' || price >= this.minPrice) && (this.maxPrice === null || price <= this.maxPrice);
                    return isInRange;
                });
            }
        
            if (this.sortByStock) {
                filteredProducts.sort((a, b) => a.stock - b.stock);
            }
        
            if (this.sortByPrice) {
                filteredProducts.sort((a, b) => a.price - b.price);
            }
        
            if (this.searchQuery) {
                const searchLowerCase = this.searchQuery.toLowerCase();
                filteredProducts = filteredProducts.filter(product => product.name.toLowerCase().includes(searchLowerCase));
            }

            this.products = filteredProducts;
        },

        logout() {
            axios.post("/api/logout")
                .then(response => {
                    window.location.href = "/index.html";
                })
                .catch(error => {
                    console.log(error);
                });
        },

        clearData() {
            this.error = '';
        },
    },
}).mount('#app'); 