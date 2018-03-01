(function () {
	$(document).ready(function () {

		$('#tweetNow').click(function () {
			console.log('lol');
			var textToTweet = $('#tweetContent').val();
			var twtLink = 'http://twitter.com/home?status=' + encodeURIComponent(textToTweet);
			var twitter = window.open(twtLink, '_blank');
			var winTimer = window.setInterval(function()
			{
				if (twitter.closed !== false)
				{
					window.clearInterval(winTimer);
					$('#twitterClaimForm').submit();
				}
			}, 200);
		});

	});
})();