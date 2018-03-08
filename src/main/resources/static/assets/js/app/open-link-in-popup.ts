export class OpenLinkInPopup {
    private _document: HTMLDocument = document;

    constructor() {
        let links = [].slice.call(this._document.querySelectorAll('a[data-open-link-in-popup]'));

        links.forEach((item: HTMLAnchorElement) => {
            item.addEventListener('click', (e) => {
                e.preventDefault();
                let newWindow = OpenLinkInPopup._getNewWindow(item.href, 600, 600);

                if (window.focus) {
                    newWindow.focus();
                }
                return false;
            });
        });
    }

    private static _getNewWindow(url, widthPopup, heightPopup) {
        let left = (screen.width / 2) - (widthPopup / 2);
        let top = (screen.height / 2) - (heightPopup / 2);
        let newWindow = window.open(null, 'popup', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + widthPopup + ', height=' + heightPopup + ', top=' + top + ', left=' + left);
        newWindow.opener = null;
        newWindow.location.assign(url);

        return newWindow;
    }
}
