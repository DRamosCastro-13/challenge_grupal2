const CLIENT = '/api/clients/current';
const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            address: "",
            phone: "",
            province: "",
            country: "",
            city: "",
            zipCode: "",
            selectedAddress: null,
            addresses: [],
            purchases: [],
            isLoggedIn: false,
            showDropdown: false,
            error: '',
        };
    },
    created() {
        this.loadData()
        this.checkLogin()
        let storageCarrito = JSON.parse(localStorage.getItem("carrito")) || [];
        this.itemsQuantity = storageCarrito.reduce((total, item) => total + item.quantity, 0);
    },
    methods: {
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
                console.error("Error loading user data:", error);
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
        loadData() {
            axios.get(CLIENT)
                .then(response => {
                    this.data = response.data;
                    console.log(this.data)
                    this.purchases = this.data.purchaseOrders;
                    this.addresses = this.data.addresses.map(address => {
                        return { ...address, expanded: false };
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        },
        createAddress() {
            body = {
                "address": this.address,
                "phone": this.phone,
                "province": this.province,
                "country": this.country,
                "city": this.city,
                "zipCode": this.zipCode
            }
            axios.post('/api/clients/newaddress', body)
                .then(response => {
                    console.log(response);
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: 'Address added successfully!',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    });
                    this.address = ""
                    this.phone = ""
                    this.province = ""
                    this.country = ""
                    this.city = ""
                    this.zipCode = ""
                })
                .catch(error => {
                    console.log(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'Error adding address!',
                        confirmButtonColor: '#d33',
                        confirmButtonText: 'OK'
                    });
                });
        },
        showAddressModal(address) {
            this.selectedAddress = address;
            this.$nextTick(() => {
                if (this.$refs.addressModal) {
                    this.$refs.addressModal.show();
                }
            });
        },
        clearSelectedAddress() {
            this.selectedAddress = null;
        },
        downloadPdf(orderNumber) {
            axios.get("/api/clients/pdf?orderNumber=" + orderNumber, { responseType: 'blob' })
                .then(response => {
                    const blob = new Blob([response.data], { type: 'application/pdf' });
                    const link = document.createElement('a');
                    link.download = `Order_${orderNumber}.pdf`;
                    link.href = window.URL.createObjectURL(blob);
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
}).mount('#app');