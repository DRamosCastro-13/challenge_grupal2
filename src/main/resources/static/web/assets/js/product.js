const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
          products: [],
          productCategory: [],
          productsSort:[],
          discount: [],
          price: 0,
          productsSale: [],
          productWithDiscount: [],
          isOpen1: false,
          isOpen2: false,
          isOpen3: false,
          isOpen4: false,
          selectedBrand: [],
          filteredBrandProducts: [],
          localStorage: [],
          quantities:1,
          saveQuantity:0,
          localStorageQuantity:0,
          search: "",
          lowStockProduct: [],
          cartItemCount: 0,
          loadingData: true,
          isLoggedIn: false,
          showDropdown: false,
          error: '',
          itemsQuantity:0
        }
    },
    created(){
        this.loadData();
        this.checkLogin()
        let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.itemsQuantity = storageCarrito.reduce((total, item) => total + item.quantity, 0);
       
    },

    mounted() {
        this.filterByBrand()
      
    },

    computed: {
        uniqueBrand() {
            return [...new Set(this.products.map(product => product.brand))];
        },
    },

    methods : {
        checkLogin() {
            axios.get('/api/clients/current')
              .then(response => {
                if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
                  this.isLoggedIn = true;
                }
                else {
                  this.isLoggedIn = false;
                }
              })
              .catch(error => {
                console.error("Error loading user data, please login", error);
              });
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
          toggleDropdown() {
            this.showDropdown = !this.showDropdown;
          },
          agregarAlCarrito(product) {
            this.checkLogin();
            // // if (!this.isLoggedIn) {
               
            //     Swal.fire({
            //         icon: "error",
            //         title: "Please log in or register to add products to the cart",
            //         text: "Redirecting you to the login page",
            //       });

            //       setTimeout(() => {
            //         window.location.href = "../pages/register.html";
            //     }, 4000);
            // // }else{
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
            let existingProductIndex = storageCarrito.findIndex(item => item.productId === product.id);
            let quantity = parseInt(product.quantities) || 1;

            if (existingProductIndex !== -1) {
                // Si el producto ya está en el carrito, actualiza su cantidad
                storageCarrito[existingProductIndex].quantity += quantity;
                this.saveQuantity = storageCarrito[existingProductIndex].quantity;
            } else {
                // Si el producto no está en el carrito, agrégalo con la cantidad especificada
                storageCarrito.push({ productId: product.id, quantity: quantity });
                this.saveQuantity = quantity;
            }

            this.itemsQuantity = storageCarrito.reduce((total, item) => total + item.quantity, 0);
        
            localStorage.setItem("carrito", JSON.stringify(storageCarrito));
            this.localStorage = storageCarrito;
        
            Swal.fire({
                position: "center",
                icon: "success",
                title: "Item added to cart",
                showConfirmButton: false,
                timer: 1500
            });
            // }            
        },
        checket(product){
            let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
            return storageCarrito.some(item => item === product.id)
        },
        loadData() {
            axios.get("/api/products")
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    this.loadingData = !this.loadingData
                    console.log(this.productsSort)
                    this.lowStockProduct= this.products.filter(product => {return product.stock <= 5})
                    console.log(this.lowStockProduct)
                    this.productsSale = this.products.filter(product => product.discount > 0)
                    this.productWithDiscount = this.productsSale.forEach(product => {
                        const sale = product.price / 100 * product.discount
                        const newPrice = product.price - sale
                        product.discount = newPrice
                        console.log(product)
                        return product
                    })
                    console.log(this.productsSale)
                   

                })
                .catch(error => {
                    console.log(error)
                })
        },

        productByCategory(category) {
            axios.get("/api/products" + category)
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    console.log(this.productsSort)
                    this.productsSale = this.products.filter(product => product.discount > 0)
                    this.productWithDiscount = this.productsSale.forEach(product => {
                        const sale = product.price / 100 * product.discount
                        const newPrice = product.price - sale
                        product.discount = newPrice
                        console.log(product)
                        return product
                    })
                    console.log(this.productsSale)
                    
                })
                .catch(error => {
                    console.log(error)
                })
        },

        categoryFilter(event) {
            const ALL = document.getElementById("ALL")
            const CPU = document.getElementById("CPU")
            const MONITOR = document.getElementById("MONITOR")
            const KEYBOARD = document.getElementById("KEYBOARD")
            const MOUSE = document.getElementById("MOUSE")
            const MOTHERBOARD = document.getElementById("MOTHERBOARD")
            const HEADPHONES = document.getElementById("HEADPHONES")
            const ACCESSORIES = document.getElementById("Accesories")

            if (event.target === ALL) {
                this.productByCategory("?")
            } else if (event.target === CPU) {
                this.productByCategory("/filtered?category=CPU")
            } else if (event.target === MONITOR) {
                this.productByCategory("/filtered?category=MONITOR")
            } else if (event.target === KEYBOARD) {
                this.productByCategory("/filtered?category=KEYBOARD")
            } else if (event.target === MOUSE) {
                this.productByCategory("/filtered?category=MOUSE")
            } else if (event.target === MOTHERBOARD) {
                this.productByCategory("/filtered?category=MOTHERBOARD")
            } else if (event.target === HEADPHONES) {
                this.productByCategory("/filtered?category=HEADPHONES")
            } else if (event.target === ACCESSORIES) {
                this.productByCategory("/filtered?category=Accesories")
            }
        },
        filterByBrand() {
            this.filteredBrandProducts = this.products.filter(product => {
                return this.selectedBrand.length === 0 || this.selectedBrand.some(brand => product.brand.toLowerCase().toUpperCase() === brand.toLowerCase().toUpperCase());
            });
        },
        
        dropDownMenu1() {
            this.isOpen1 = !this.isOpen1;
        },
        dropDownMenu2() {
            this.isOpen2 = !this.isOpen2;
        },
        dropDownMenu3() {
            this.isOpen3 = !this.isOpen3;
        },
        dropDownMenu4() {
            this.isOpen4 = !this.isOpen4;
        },

        saveSearch(event) {
            this.search = event.target.value
            this.searchFilter()
        },

        searchFilter() {
            this.filteredBrandProducts = this.products.filter(product => product.name.toLowerCase().includes(this.search.toLowerCase()))
        },
        
    }

})

app.mount("#app")
