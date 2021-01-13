function testSSL() {
  const httpsAgent = new https.Agent({
    rejectUnauthorized: false,
  });
  fetch("https://www.hustledance.ru/",{
    agent:httpsAgent
  })
  .then(function (resp) {
    console.log(resp)
  })

}