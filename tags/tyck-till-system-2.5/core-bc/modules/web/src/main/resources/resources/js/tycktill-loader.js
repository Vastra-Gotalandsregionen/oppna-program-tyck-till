/*

PURPOSE & USAGE
===============
This scripts purpose is to dynamically load needed javascripts and CSS files
for the "tycktill" service. It will load jQuery, jQuery UI Dialog + CSS and
tycktill.js. 

Before loading it will check for availability, i.e. if target site already has
jQuery installed for instance.  

To use in any page you need to do two things:

#1 inlcude this script as a regular script.
<script src="htpp://host/path/to/js/tycktill-loader.js" type="text/javascript" language="javascript" charset="utf-8"></script>    

#2 Add at least one link/button/element with class "tycktill" 
   (see tycktill.js for variations and options)
<a href="http://path/to/page" data-url="http://path/to/iframe/" title="Dialog title">Tyck till!</a>


If your site already contains jQuery and jQuery UI (with Dialog) then you can 
skip this file and just load "js/tycktill.js" and the CSS "style/jquery-ui-dialog.css"

*/
(function(){

//WARNING: this loader depends on it's file path ending with "js/tycktill-loader.js", 
//if that is changed it will fail 

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

//find out our base url
var scripts = document.getElementsByTagName('script');
var base = "";
for (var i=0;i<scripts.length; i++) {
    if (scripts[i].src && endsWith(scripts[i].src,'tycktill-loader.js')) {
        base = scripts[i].src.substring(0,scripts[i].src.length - 21);
    }
}

var wait_until = function(test,callback) {
    if (test()) {
        callback();
    } else {
        setTimeout(function(){
            wait_until(test,callback);
        },100);
    }
};

var load_script = function(src) {
  var script = document.createElement('script');
  script.setAttribute('src', src);
  document.getElementsByTagName('head')[0].appendChild(script);
};

var load_css = function(src) {
  var link = document.createElement('link');
  link.setAttribute("rel", "stylesheet");
  link.setAttribute("type", "text/css");
  link.setAttribute("href", src);
  document.getElementsByTagName('head')[0].appendChild(link);
};

//first check jQuery 
if (typeof jQuery == 'undefined') {
    //load jQuery
    load_script(base+'js/jquery-noconflict.js');
} 

//we have to wait for jQuery to load
wait_until(
    function(){ return typeof jQuery != 'undefined' },
    function(){
        
        if (typeof jQuery.fn.dialog == 'undefined') {
            //load jQuery UI Dialog + css
            load_script(base+'js/jquery-ui-1.8.6.custom.min.js');
            load_css(base+'style/smoothness/jquery-ui-1.8.16.custom.css');
            load_css(base+'style/dialog.css');
        } 
        
        wait_until(
            function(){ return typeof jQuery.fn.dialog != 'undefined' },
            function(){
                if (typeof jQuery.fn.tycktill == 'undefined') {
                    //load jQuery UI Dialog + css
                    load_script(base+'js/jquery.tycktill.js');
                } 
            }
        );
        
    }
);

})();


