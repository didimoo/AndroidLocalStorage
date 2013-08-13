AndroidLocalStorage
===================

LocalStorage is partially broken in Android webviews : Local storage is supposed to be a persistent storage available **accros all tabs** (or windows) of a browser.
On Android, LocalStorage works well but only in the current webview. Multiple webviews of the same app can't share the same data with LocalStorage.
That is sad !


JavaScriptInterface to the rescue !
===================================

This sample Android app shows you how you can fix the webview LocalStorage with a JavaScriptInterface.

What we do here is :

* Create an Android database with a simple key/value table
* Create a JavaScriptInterface which implements all LocalStorage's methods (getItem, setItem, removeItem, clear)
* Add it to the webview
* Replace the JavaScript LocalStorage with this "new one" so that you don't have to change the way you use your localStorage usually.

How to use it in your own project ?
===================================

* Include the LocalStorage.java
* Create the JavaScriptInterface and add it to the webview as shown in MainFragment.java
* Bind the JavaScript LocalStorage to the new one as shown in assets/index.html

This last step is as simple as this :

    <script type="text/javascript">
	        try{
	            //we replace default localStorage with our Android Database one
	            window.localStorage=LocalStorage;    
	        }catch(e){
	            //LocalStorage class was not found. be sure to add it to the webview
			         	alert("LocalStorage ERROR : can't find android class LocalStorage. switching to raw localStorage")		        
			      }
        //then use your localStorage as usual
        localStorage.setItem('foo','it works')
        alert(localStorage.getItem('foo'))
        
    </script>



Play with this example project to see how it works. 

