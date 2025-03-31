package gg.jte.generated.ondemand.urls;
import hexlet.code.utils.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,6,6,19,19,21,21,21,22,22,22,22,22,22,22,22,22,22,22,22,23,23,23,24,24,24,26,26,30,30,30,30,30,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.urls.UrlsPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <div class=\"container-lg mt-5\">\r\n            <h1>Сайты</h1>\r\n            <table class=\"table table-bordered table-hover mt-3\">\r\n                <thead>\r\n                    <tr>\r\n                        <th class=\"col-1\">ID</th>\r\n                        <th>Имя</th>\r\n                        <th class=\"col-2\">Последняя проверка</th>\r\n                        <th class=\"col-1\">Код ответа</th>\r\n                    </tr>\r\n                </thead>\r\n                <tbody>\r\n                ");
				for (var entry : page.getUrls().entrySet()) {
					jteOutput.writeContent("\r\n                    <tr>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(entry.getKey().getId());
					jteOutput.writeContent("</td>\r\n                        <td><a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(entry.getKey().getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(entry.getKey().getName());
					jteOutput.writeContent("</a></td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(entry.getValue().getCreatedAt());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(entry.getValue().getStatusCode());
					jteOutput.writeContent("</td>\r\n                    </tr>\r\n                ");
				}
				jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n    ");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.urls.UrlsPage page = (hexlet.code.dto.urls.UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
