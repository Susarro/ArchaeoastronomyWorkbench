from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app



class MainPage(webapp.RequestHandler):
    def get(selfself):
        self.response.out.write("""
        <html>
        <head>
        <title>HelloWorldFX</title>
        </head>
        <body>
        <h1>HelloWorldFX</h1>
        <script src="http://dl.javafx.com/dtfx.js"></script>
        <script>
            javafx(
                {
                    archive: "/javafx/HelloWorldFX.jar",
                    width: 240,
                    height: 320,
                    code: "helloworldfx.HelloWorldFX",
                    name: "HelloWorldFX"
                }
            );
        </script>
        </body>
        </html>""")

application = webapp.WSGIApplication([('/', MainPage)], debug=True)

def main():run_wsgi_app(application)

if __name__ == "__main__": main()


