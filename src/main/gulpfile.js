var gulp        = require('gulp');
var browserSync = require('browser-sync').create();

// Static server
gulp.task('browser-sync', function() {
    browserSync.init({
        server: {
            baseDir: "./webapp/",
            index: "login.html"
        }
    });
    gulp.watch("./webapp/**.html").on('change', browserSync.reload);
    gulp.watch("./webapp/**/**.js").on('change', browserSync.reload);
    gulp.watch("./webapp/**/**.css").on('change', browserSync.reload);

});

gulp.task('default',['browser-sync']);
