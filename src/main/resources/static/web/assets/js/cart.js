const { createApp } = Vue

const options = {
  data() {
    return {
      productos: [],
    
      localStorageCart: [],
      modalHVisible:false,
      modalVisibleAlert:false,
      items:[],
      comment:'',
      discount:0,
      itemsFiltrados:[],
      isLoggedIn: false,
      showDropdown: false,
      error: '',
      areAddresses:false
    
    }
  },

  created() {
    this.loadData()
    this.checkLogin()
  },//Create
  mounted(){
    document.addEventListener("DOMContentLoaded", function() {
      const activeNavItem = document.querySelector('.active')
      if (activeNavItem) {
          activeNavItem.classList.add('border-b','border-slate-400')
      }
  })
  },
  methods: {
    loadData() {
      axios.get('/api/products')
        .then(data => {
          this.productos = data.data
          this.localStorageCart = JSON.parse(localStorage.getItem('carrito')) || [];
          console.log(this.localStorageCart)
          // Filtra los artículos que estan en el carrito
        this.localStorageFiltrado()
        this.addQuantity()
        })
        .catch(error => console.log(error))
    },
    checkLogin() {
      axios.get('/api/clients/current')
        .then(response => {
          if (response.data.role == "CLIENT" || response.data.role == "ADMIN") {
            this.isLoggedIn = true;
            if(response.data.addresses.length >= 1){
              this.areAddresses = true
            } else{
              this.areAddresses = false
            }
          }
          else {
            this.isLoggedIn = false
          }
        })
        .catch(error => {
          console.error("Error loading user data, please login", error);
        });
    },
    notLoggedIn(){
      if (!this.isLoggedIn) {        
        Swal.fire({
            icon: "error",
            title: "Please log in or register to add products to the cart",
            text: "Redirecting you to the login page",
          });
          setTimeout(() => {
            window.location.href = "../pages/register.html";
        }, 4000);
        return 'not-ok'
      }
      return 'ok'
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
    addQuantity() {
      this.itemsFiltrados.forEach(producto => {
        const cantidadEnCarrito = this.localStorageCart.find(storage => storage.productId === producto.id)
        if (cantidadEnCarrito) {
        producto.cantidadEnCarrito = cantidadEnCarrito.quantity
        console.log(producto.cantidadEnCarrito)
       }
      })
      
    },
    localStorageFiltrado(){
     this.itemsFiltrados= this.productos.filter(producto => this.localStorageCart.some(storage => storage.productId === producto.id))
     console.log(this.itemsFiltrados)
    },  
    removerDelCarro(producto, accion) {
      let storageCarrito = JSON.parse(localStorage.getItem('carrito')) || [];
      const index = storageCarrito.findIndex(item => item.productId === producto.id);

      if (accion === 'restar') {
        this.restar(producto);
      } else if (accion === 'sumar') {
        this.sumar(producto);
      }

      // Actualiza la cantidad en el localStorage
      if (index !== -1) {
      
        // Elimina el elemento si la cantidad es cero
        if (producto.cantidadEnCarrito === 0) {
          storageCarrito.splice(index, 1);
        } else {
          storageCarrito[index].quantity = producto.cantidadEnCarrito;
        }
      } else if (producto.cantidadEnCarrito > 0) {
        // Agrega el elemento solo si la cantidad es mayor a cero
        storageCarrito.push({ productId: producto.id, quantity: producto.cantidadEnCarrito });
      }
      localStorage.setItem('carrito', JSON.stringify(storageCarrito));
      this.localStorage = storageCarrito
    }, //aca termina removerDelCarro
    totalPorproducto(producto) {
      let total = producto.cantidadEnCarrito * producto.price
      return this.dotsNumbers(total)
    }, // finaliza totalPorproducto
    restar(producto) {
      if (producto.cantidadEnCarrito > 0) {
        producto.cantidadEnCarrito--
      }
    },//finaliza restar
    sumar(producto) {
      if (producto.cantidadEnCarrito < producto.stock) {
        producto.cantidadEnCarrito++
      }
    },// finaliza sumar
    dotsNumbers(number) {
      // se verifica que los datos sean distinto de undefined y null
      // luego se usa el metodo toLocaleString que convierte
      // un string segun la region establecida, se le da
      // el stylo de moneda y se selecciona USD como moneda
      //ademas se establece que no debe mostrar numeros fraccionados.
      if (number !== undefined && number !== null) {
        return number.toLocaleString("es-MX", {
          style: "currency",
          currency: "USD",
          minimumFractionDigits: 0,
        })
      }
    },//fin del dotsNumbers
    graciasPorSuCompra() {
      alert("Thanks for your purchase!")
    }, //finaliza graciasporsucompra
    //inicia menu hamburguesa
    abrirModalHamb() {
      this.modalHVisible = true
    },
    cerrarModalHamb() {
      this.modalHVisible = false
    },
    //finaliza menu hamburguesa
    abrirAlert() {
      this.modalVisibleAlert = true
      if (this.modalVisibleAlert) {
          document.body.classList.add('overflow-y-hidden')
      }
  }, // finaliza showModal
  cerrarAlert() {
      this.modalVisibleAlert = false
      if (this.modalVisibleAlert == false) {
          document.body.classList.remove('overflow-y-hidden')         
      }
  },
  addressCheck(){
    if(!this.areAddresses){
      Swal.fire({
        icon: "error",
        title: "Please register an address before proceed",
        text: "Redirecting you to profile page",
      });
      setTimeout(() => {
        window.location.href = "../pages/profile.html";
      }, 4000);
      return 'not ok'
    }
    return 'ok'
  },
  redirigir() {
    if(this.notLoggedIn() === 'ok' && this.addressCheck() === 'ok'){
      this.saveCartAmount()
      window.location.href = "/web/payment/cardPayment.html"
    }
  },// finaliza cerrarModal
  saveCartAmount(){
    let cleanedAmount = this.saveAmount.replace(/[^\d.]/g, '')
    localStorage.setItem('amount',JSON.stringify(cleanedAmount))
  }
},//finaliza methods
computed: {
  totalCarrito() {
    let total = 0
    for (let i = 0; i < this.itemsFiltrados.length; i++) {
      const producto = this.itemsFiltrados[i]
      total += producto.cantidadEnCarrito * producto.price
    }
    return this.dotsNumbers(total)
  }, //aca finaliza totalCarrito
  saveAmount() {
    let totalCarrito = this.totalCarrito
    return totalCarrito;
  },
  },//aca finaliza el computed
}//finalizacion de options

const app = createApp(options)
app.mount('#app')
