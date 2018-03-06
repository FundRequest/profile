export class Utils {

    public static showLoading() {
        document.querySelector('[data-page-loader]').classList.remove('d-none');
    }

    public static hideLoading() {
        document.querySelector('[data-page-loader]').classList.add('d-none');
    }

}
