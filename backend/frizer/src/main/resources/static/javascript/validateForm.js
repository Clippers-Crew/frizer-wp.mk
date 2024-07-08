function validateForm() {
    const ratingInput = document.querySelector(".reviewAddForm #rating")

    if (ratingInput) {
        const ratingValue = parseFloat(ratingInput.value);
        if (!isNaN(ratingValue) && ratingValue >= 0 && ratingValue <= 5) {
            return true;
        }
    }
    alert("Оценката мора да биде помеѓу 0 и 5")
    return false;
}

function validateRegisterForm() {
    const password = document.querySelector("#registerForm #password").value;
    const repeatedPassword = document.querySelector("#registerForm #repeatedPassword").value;
    const phoneNumberInput = document.querySelector("#registerForm #phoneNumber").value;

    const isPhoneOk = isPhoneOk(phoneNumberInput);

    if (!isPhoneOk) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    if (!isPasswordOk(password)) {
        alert("Лозинката мора да има барем 8 карактери, една голема буква, една мала буква, еден број и еден специјален карактер.");
        return false;
    }

    if (password !== repeatedPassword) {
        alert("Лозинките не се совпаѓаат.");
        return false;
    }

    return true;
}

function validateProfileEditForm() {
    const firstName = document.querySelector("#profileEditForm #firstName").value;
    const lastName = document.querySelector("#profileEditForm #lastName").value;
    const phoneNumber = document.querySelector("#profileEditForm #phoneNumber").value;

    firstNameOk = isNameOk(firstName)
    if (!firstNameOk) {
        alert("Вашето име треба да не е празно и да содржи само букви.");
        return false;
    }
    lastNameOk = isNameOk(lastName)
    if (!lastNameOk) {
        alert("Вашето презиме треба да не е празно и да содржи само букви.");
        return false;
    }
    phoneNumberOk = isPhoneOk(phoneNumber)
    if (!phoneNumberOk) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    return true;
}

function isNameOk(name) {
    const hasOnlyLetters = /^[A-Za-z]+$/.test(name);
    return name !== null && name.length > 0 && hasOnlyLetters;
}

function isPhoneOk(phone) {
    return (phone.length === 9 && phone.startsWith("07")) ||
        (phone.length === 12 && phone.startsWith("+3897"));
}


function isPasswordOk(password) {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
}
