const browseButtons = document.querySelectorAll(".browse-button");
const browseMenus = document.querySelectorAll(".browse-menu");

browseButtons.forEach((browseButton, index) => {
    browseButton.addEventListener("click", () => {
        // Close all browseMenus
        browseMenus.forEach((menu, menuIndex) => {
            if (menuIndex !== index) {
                menu.classList.add("display_none");
            }
        });

        // Toggle the display of the clicked browseMenu
        browseMenus[index].classList.toggle("display_none");
    });
});

// // Get all the radio buttons
// const radioButtons = document.querySelectorAll('input[name="sort"]');
//
// // Add event listener to each radio button
// radioButtons.forEach(radioButton => {
//     radioButton.addEventListener('change', () => {
//         // Reload the page when a radio button is checked
//         location.reload();
//     });
// });