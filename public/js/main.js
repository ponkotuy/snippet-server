window.onload = () => {
  new Vue({
    el: '#snippet_form',
    data: {
      snippet: ''
    },
    methods: {
      isDisabled: function() { return this.snippet === "" },
      submit: function () {
        if(this.snippet === "") return;
        const body = `snippet=${this.snippet}`;
        fetch('/snippet', {
          body: body,
          method: 'POST',
          headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'}
        }).then(res => {
          if (res.ok) {
            res.text().then( (str) => location.href = `/snippet/${str}` )
          }
        });
      }
    }
  });
};
