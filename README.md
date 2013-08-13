AndroidLocalStorage
===================

Local Storage is partially broken in Android webivews : Local storage is supposed to be a persistent storage available **accros all tabs** (or windows) of a browser.
On Android, localStorage works well but only in the current webview. multiple webviews of the same app can't share the same data with localStorage.
that is sad !


JavascriptInterface to the rescue !
===================================

this sample Android app shows you how you can fix the webview localStorage with the javascript interface.

What we do here is :

* create an Android database with a simple key/value table
* create a javascript interface which implements all localStorage methods (getItem, setItem, removeItem, clear)
* adds it to the webview
* replace the javascript localStorage with this new one so that you don't have to change the way you use your localStorage
 
How to use ?
============

* include the LocalStorage.java
* create the interface and add it to the webview as shown in mainFragment.java
* bind the javascript localStorage to the new one as shown in assets/index.html

this last step is a simple as this :

    <script type="text/javascript">
        try{
            //we replace default localStorage with our Android Database one
            window.localStorage=LocalStorage;    
        }catch(e){
            //LocalStorage class was not found. be sure to add it to the webview
            alert('failed')
        }
        //the use your localStorage as usually
        localStorage.setItem('foo','it works')
        alert(localStorage.getItem('foo'))
        
    </script>



play with this example project to see how it works. 

