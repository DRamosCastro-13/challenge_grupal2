const { createApp } = Vue

const options ={
    data() {
        return {
            products: [],
            productCategory: [],
            productsSort: [],
            discount: [],
            price: 0,
            productsSale: [],
            productWithDiscount: [],
            isOpen1: false,
            isOpen2: false,
            isOpen3: false,
            isOpen4: false,
            selectedBrand: [],
            isLoggedIn: false,
            showDropdown: false,
            error: '',
            productsSaleOnIndex:[],
            currentIndex: 0,
            intervalId: null,
            intervalDuration: 2000, 
            visibleItems: 4,
            carouselProducts: [],
            isCarouselPaused: false,

           
        }
    },
    created() {
        this.loadData()

    },
  created() {
    this.getproducts()
  },
    computed: {
      productsWithDiscounts() {
        return this.products.filter(product => product.discount > 0);
      },
      productsWithDiscountOnIndex() {
        this.productsSaleOnIndex= this.productsSale.slice(0,17)
                return this.productsSaleOnIndex
      },  
      currentOffset() {
        return (this.currentIndex * (100 / this.visibleItems));
      },

     
    },

    mounted() {
      console.log('Component mounted');
      this.carouselProducts = this.generateCarouselProducts()
      this.startCarousel()
    },

    methods: {
    loadData() {
          this.checkLogin() || false;
        },
        checkLogin() {
          axios.get('/api/clients/current')
            .then(response => {
              if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
                this.isLoggedIn = true;
                console.log(this.isLoggedIn);
              }
              else {
                this.isLoggedIn = false;
                console.log(this.isLoggedIn);
              }
            })
            .catch(error => {
              console.error("Error loading user data:", error);
            });
        },
        toggleDropdown() {
          this.showDropdown = !this.showDropdown;
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
        getproducts() {
            axios.get("/api/products")
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

              generateCarouselProducts() {
                const carouselProducts = [];
                let startIndex = this.currentIndex;
            
                for (let i = 0; i < this.visibleItems; i++) {
                  carouselProducts.push(this.productsSaleOnIndex[startIndex]);
            
                  startIndex++;
                  if (startIndex >= this.productsSaleOnIndex.length) {
                    startIndex = 0; // Loop back to the beginning
                  }
                }
            
                return carouselProducts;
              },

              startCarousel() {
                this.intervalId = setInterval(() => {
                    if (!this.isCarouselPaused) {
                        console.log("start slide");
                        this.currentIndex++;
                        if (this.currentIndex >= this.productsSaleOnIndex.length) {
                            this.currentIndex = 0; // Loop back to the beginning
                        }
                        this.carouselProducts = this.generateCarouselProducts();
                    }
                }, this.intervalDuration);
              }, 
              handleProductHover(isHovered) {
                this.isCarouselPaused = isHovered;
              },
        
          },
              beforeDestroy() {
                clearInterval(this.intervalId);
              }


     // fin methods
} // fin create app
const app = createApp(options)
app.mount("#app")