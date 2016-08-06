You can switch browsers by -D:
-Dbrowser=phantomjs
-Dbrowser=chrome

Firefox is default

PhantomJs has uncovered by JUL logging output to stderr,
you can duplicate its log to file:
-Dphantomjs.log=/path/to/log