# Beer App
![License](https://img.shields.io/github/license/Lambda3/dotnet-commands.svg)

This App has been developed as part of a Exercise to consume the [PUNK API](https://punkapi.com/) one example is of its data is [this](https://api.punkapi.com/v2/beers?page=10). In this project I applyed my skills in building layouts and navigation files by building a Bear App. The app is building with three screens:

* Login
* Beer list
* Beer detail



|Login|Beers catalog|Beer detail|
|---|---|---|
|![login](https://github.com/luismikg//Beer-Applicaion/images/images/login.jpeg)|![list](https://github.com/luismikg//Beer-Applicaion/images/images/list_beer.jpeg)|![detail](https://github.com/luismikg//Beer-Applicaion/images/images/detail_beer.jpeg)|



---

## App 
Designed for Phones and NOT for Tablets

---

### Layouts
      
* Layouts using the correct ViewGroups and Views in an Android app.
	* The project correctly implements LinearLayout and ConstraintLayout to match the complexity of the layout of a page. 
  
* Databinding in Layouts to show the correct data to users in multiple layouts.
	1. All layouts will use the <layout> tag to support Databinding.
	2. Detail screen uses the <data> element.

### Navigation

* Use a navigation file that correctly takes a user from one page to the next in an Android app
	* The app needs to traverse the following screens in the correct order:
      	* Login
      	* Listing screen
      	* Detail screens
            The app should also be able to navigate back via the back arrow or the back button.
      	* A navigation file has been created that defines a start destination.
      	* All destinations have a fragment, label and action associated with it

* Use Databinding for click listeners on a navigation screen in an Android app.
	1. All code will use the DataBindingUtil class to inflate the layout.
	2. All click listeners are connected via the DataBindingUtil class and uses the NavController to navigate to the next screen.   
    
---

## Icon and Image credits

* Beer App Icon is from: Freepik from the [Freepik](https://www.freepik.com).
* Beer dummy Icon is from: Romli Ahmad from the  [Noun Project](https://thenounproject.com/).
* Like Icon is from: Trang5000 from the  [Noun Project](https://thenounproject.com/).
* Other images: [dribbble](https://dribbble.com/) and [Google Material Desinger](https://material.io/resources/icons/?style=baseline)
