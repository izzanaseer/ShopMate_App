# ShopMate Android App

A mobile application built with Android Studio that allows users to manage their shopping lists. The app uses Firebase for authentication and real-time database management. It is designed to provide a smooth and user-friendly experience with real-time updates and responsive layouts.

---

## Features

### 1. Firebase Authentication
- **Login and Registration**: Secure user login and registration using email and password.
- **Forgot Password**: Allows users to reset their passwords via email.
- **Logout**: Provides a secure logout option to sign users out of their accounts.
- **Secure Access**: Shopping lists are accessible only after user authentication.

### 2. Shopping List Page (Firestore + FirebaseRecyclerView)
- Displays a list of shopping items dynamically using Firestore as the backend.
- **Real-time Updates**: Items are displayed using `FirebaseRecyclerView` and `FirebaseRecyclerOptions`.
- Each item includes:
  - **Item Name**
  - **Quantity**
  - **Price**
- **Delete Button**: Removes an item from Firestore and automatically decrements IDs of remaining items.

### 3. Add New Item (Realtime Database)
- Provides a form or alertDialog for adding shopping items to Firebase Realtime Database when FAB button is clicked.
- **Form Fields**:
  - Item Name
  - Quantity
  - Price
- Automatically generates a unique ID for each item.

### 4. Splash Screen
- **Animations**:
  - Translate Animation: Moves the app logo from the top to the center of the screen.
  - Scale Animation: Slightly grows the logo during the animation.
- Navigates to the next screen after the animation.

### 5. Responsive Design
- Fully responsive layouts using `ConstraintLayout`.
- Supports both **portrait** and **landscape** orientations.

---

## Installation

1. Open the project in Android Studio.
2. Sync the Gradle files.
3. Configure Firebase:
   - Add the `google-services.json` file to the `app` folder.
   - Enable **Firebase Authentication**, **Realtime Database**, and **Firestore** in your Firebase project.
4. Run the app on an emulator or physical device.

---

## Technologies Used

- **Android Studio**: For app development.
- **Firebase**:
  - Firebase Authentication
  - Firebase Realtime Database
  - Firebase Firestore
  - FirebaseRecyclerView
  - FirebaseRecyclerOptions
- **Java**: Programming language.
- **ConstraintLayout**: For responsive designs.

---

## Guidelines Followed
- All strings are stored in `res/values/strings.xml`.
- All dimensions are defined in `res/values/dimens.xml`.
- Colors are specified in `res/values/colors.xml`.
- Consistent design with uniform spacing, colors, and fonts.

---

## Deliverables
1. **Authentication Screen**:
   - Login and Registration with "Forgot Password" functionality.
   - Logout functionality to securely sign users out of their accounts.
2. **Shopping List Page**:
   - Displays items with real-time updates and a delete button.
3. **Add Item Page**:
   - Form for adding shopping items to Realtime Database with validation.
4. **Splash Screen**:
   - Features Translate and Scale animations.

---

## Future Enhancements
- Implement sorting and filtering for the shopping list.
- Add a search functionality for items.
- Include user profile management.
