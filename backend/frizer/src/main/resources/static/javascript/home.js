/*=============== SHOW MENU ===============*/
const navMenu = document.getElementById('nav-menu'),
      navToggle = document.getElementById('nav-toggle'),
      navClose = document.getElementById('nav-close'),
      advancedToggle = document.getElementById('advanced-toggle'),
      advancedMenu = document.getElementById('advanced-menu')

/* Menu show */
if(navToggle){
   navToggle.addEventListener('click', () =>{
      navMenu.classList.add('show-menu')
   })
}

if(advancedToggle){
   advancedToggle.addEventListener('click', () => {
      if(advancedMenu.classList.contains('show-advanced-menu')){
         advancedMenu.classList.remove('show-advanced-menu');
      }
      else{
         advancedMenu.classList.add('show-advanced-menu')
      }
   })
}

/* Menu hidden */
if(navClose){
   navClose.addEventListener('click', () =>{
      navMenu.classList.remove('show-menu')
   })
}

