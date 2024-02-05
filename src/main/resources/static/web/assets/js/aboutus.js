const { createApp } = Vue

const options = {
  data() {
    return {
      articulos: [],
    
      localStorageCart: [],
      localStorageFiltrado: [],
      modalHVisible:false,
      modalVisibleAlert:false,
      isLoggedIn: false,
      showDropdown: false,
      error: '',
    }
  },

  created() {
    this.checkLogin()
  },
  mounted(){
    document.addEventListener("DOMContentLoaded", function() {
      const activeNavItem = document.querySelector('.active')
      if (activeNavItem) {
          activeNavItem.classList.add('border-b','border-slate-400')
      }
  })
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
    removerDelCarro(articulo, accion) {
      let storageCarrito = JSON.parse(localStorage.getItem('carrito')) || [];
      const index = storageCarrito.findIndex(item => item.id === articulo._id);

      if (accion === 'restar') {
        this.restar(articulo);
      } else if (accion === 'sumar') {
        this.sumar(articulo);
      }

      // Actualiza la cantidad en el localStorage
      if (index !== -1) {
        storageCarrito[index].cantidad = articulo.cantidadEnCarrito;

        // Elimina el elemento si la cantidad es cero
        if (articulo.cantidadEnCarrito === 0) {
          storageCarrito.splice(index, 1);
        }
      } else if (articulo.cantidadEnCarrito > 0) {
        // Agrega el elemento solo si la cantidad es mayor a cero
        storageCarrito.push({ id: articulo._id, cantidad: articulo.cantidadEnCarrito });
      }
      localStorage.setItem('carrito', JSON.stringify(storageCarrito));
      this.localStorage = storageCarrito
    }, //aca termina removerDelCarro
    totalPorArticulo(articulo) {
      let total = articulo.cantidadEnCarrito * articulo.precio
      return this.dotsNumbers(total)
    }, // finaliza totalPorArticulo
    restar(articulo) {
      if (articulo.cantidadEnCarrito > 0) {
        articulo.cantidadEnCarrito--
      }
    },//finaliza restar
    sumar(articulo) {
      if (articulo.cantidadEnCarrito < articulo.disponibles) {
        articulo.cantidadEnCarrito++
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
          currency: "ARS",
          minimumFractingDigits: 0,
        })
      }
    },//fin del dotsNumbers
    graciasPorSuCompra() {
      alert("gracias por su compra")
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
  },// finaliza cerrarModal
  },//finaliza methods
  computed: {
    totalCarrito() {
      let total = 0
      for (let i = 0; i < this.localStorageFiltrado.length; i++) {
        const articulo = this.localStorageFiltrado[i]
        total += articulo.cantidadEnCarrito * articulo.precio
      }
      return this.dotsNumbers(total)
    }, //aca finaliza totalCarrito
  },//aca finaliza el computed
}//finalizacion de options

const app = createApp(options)
app.mount('#app')
