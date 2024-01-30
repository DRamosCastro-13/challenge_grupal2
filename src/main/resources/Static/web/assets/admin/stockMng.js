const {createApp} = Vue

let app = createApp({
    data(){
        return {
            products : [],
            error: '',
            name : '',
            brand : '',
            imgUrl : '',
            description : '',
            category : '',
            price : '',
            discount : '',
            stock : '',
           
        }
    },
    created(){
        this.loadData()
        
    },

    methods: {
        loadData(){
            axios.get("/api/products")
            .then(response => {
                this.products = response.data
                console.log(this.products)
            })
            .catch(error => {
                console.log(error)
            })
        },
        logout(){
            axios.post("/api/logout")
            .then(response => {
                window.location.href="/index.html"
            }).catch(error => {
                console.log(error)
            })
        },
        addNewProduct(){
            this.error = '';

            if (!this.name || !this.brand || !this.imgUrl || !this.description || !this.category || !this.price || this.discount < 0 || !this.stock){
                this.error = "Please fill in all fields";
                return;
            }

            const confirmationMessage = `You are about to add ${this.stock} units of ${this.name} to the inventory. Are you sure?`;

            Swal.fire({
                title: "Add new product?",
                text: confirmationMessage,
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Proceed"
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/products/new",{
                        "name" : this.name,
                        "brand" : this.brand,
                        "img_url" : this.imgUrl,
                        "description" : this.description,
                        "productCategory" : this.category,
                        "price" : this.price,
                        "discount" : this.discount,
                        "stock" : this.stock
                    })
                        .then(response => {
                            Swal.fire({
                                title: "Success",
                                text: "Product added successfully",
                                icon: "success"
                            });
        
                            setTimeout(() => {
                                window.location.href="/web/assets/admin/overviewAdmin.html";
                                this.clearData();
                            }, 2000);
                            console.log(response);
                        })
                        .catch(error => {
                            this.error = error.response.data;
                            console.log(error);
                        });
                }
            });
        },
        clearData(){
            this.name = '',
            this.brand = '',
            this.imgUrl = '',
            this.description = '',
            this.category = '',
            this.price = '',
            this.discount = '',
            this.stock = '',
            this.error = ''
        }
    }
}).mount('#app')
