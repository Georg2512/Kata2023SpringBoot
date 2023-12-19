async function openAndFillInTheModal(form, modal, id) {
    modal.show();
    let user = await getOneUser(id);
    form.id.value = user.id;
    form.firstname.value = user.firstname;
    form.lastname.value = user.lastname;
    form.email.value = user.email;
    form.username.value = user.username;
    form.password.value = user.password;
    form.roles.value = user.roles.join(',');
}