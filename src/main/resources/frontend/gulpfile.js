// fetch command line arguments
let dev = false;
const arg = (argList => {
    let arg = {}, a, opt, thisOpt, curOpt;
    for (a = 0; a < argList.length; a++) {

        thisOpt = argList[a].trim();
        opt = thisOpt.replace(/^\-+/, '');

        if (opt === thisOpt) {

            // argument value
            if (curOpt) arg[curOpt] = opt;
            curOpt = null;

        }
        else {

            // argument name
            curOpt = opt;
            arg[curOpt] = true;
        }
    }
    return arg;
})(process.argv);


const gulp = require('gulp'),
    sass = require('gulp-sass'),
    gulpif = require('gulp-if'),
    rename = require('gulp-rename'),
    autoprefixer = require('gulp-autoprefixer'),
    cssnano = require('gulp-cssnano'),
    plumber = require('gulp-plumber'),
    notify = require('gulp-notify'),
    sassLint = require('gulp-sass-lint'),
    sourcemaps = require('gulp-sourcemaps'),
    tildeImporter = require('node-sass-tilde-importer'),
    runSequence = require('run-sequence');

let target = "../static/assets";

let displayError = function(error) {
    // Initial building up of the error
    var errorString = '[' + error.plugin.error.bold + ']';
    errorString += ' ' + error.message.replace("\n", ''); // Removes new line at the end

    // If the error contains the filename or line number add it to the string
    if (error.fileName)
        errorString += ' in ' + error.fileName;

    if (error.lineNumber)
        errorString += ' on line ' + error.lineNumber.bold;

    // This will output an error like the following:
    // [gulp-sass] error message in file_name on line 1
    console.error(errorString);
};

let onError = function(err) {
    notify.onError({
        title: "Gulp",
        subtitle: "Failure!",
        message: "Error: <%= error.message %>",
        sound: "Basso"
    })(err);
    this.emit('end');
};

let sassOptions = {
    outputStyle: 'expanded',
    importer: tildeImporter
};

let autoprefixerConfig = {
    browsers: ['last 3 versions']
};
let cssnanoConfig = {
    reduceIdents: false
};

function runSass(filename) {
    return gulp.src(filename)
        .pipe(plumber({errorHandler: onError}))
        .pipe(sass(sassOptions))
        .pipe(autoprefixer(autoprefixerConfig))
        .pipe(gulp.dest(`${target}/css`))
        .pipe(cssnano(cssnanoConfig))
        .pipe(autoprefixer(autoprefixerConfig))
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(`${target}/css`));
}

function runTs(tsConfig, filename) {
    let tsProject = ts.createProject(tsConfig);
    let tsResult = gulp.src(filename)
        .pipe(tsProject());

    return tsResult.js.pipe(gulp.dest(`${target}/js`));
}

gulp.task('scripts', function() {
    return runTs('tsconfig.json', 'js/**/*.ts');
});

gulp.task('copy-assets', function() {
    let x = gulp.src(['fonts/**/*']).pipe(gulp.dest(`${target}/fonts`));
    let y = gulp.src(['webfonts/**/*']).pipe(gulp.dest(`${target}/webfonts`));
    let z = gulp.src(['img/**/*']).pipe(gulp.dest(`${target}/img`));
    let a = gulp.src(['js/**/*.js']).pipe(gulp.dest(`${target}/js`));
    return [x, y, z, a];
});

gulp.task('styles-core', function() {
    return runSass('scss/core.scss')
});

gulp.task('styles-general', function() {
    return runSass('scss/general.scss');
});

gulp.task('styles-website', function() {
    return runSass('scss/website.scss');
});

gulp.task('watch', function() {
    gulp.watch(['scss/fundrequest/*.scss', '!scss/fundrequest/website/*.scss'], ['styles-general']);
    gulp.watch('scss/fundrequest/website/*.scss', ['styles-website']);
});

gulp.task('default', function(done) {
    target = (arg && arg.target) || target;
    runSequence('styles-core', 'styles-general', 'styles-website', 'watch', done);
});

gulp.task('dev-default', function(done) {
    target = (arg && arg.target) || target;
    //dev = true;
    runSequence('styles-general', 'styles-website', 'watch', done);
});