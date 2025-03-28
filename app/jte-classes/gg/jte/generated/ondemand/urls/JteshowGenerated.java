package gg.jte.generated.ondemand.urls;
import hexlet.code.utils.LocalDateTimeFormatter;
import hexlet.code.utils.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,4,4,6,6,11,11,11,15,15,15,19,19,19,23,23,23,23,23,23,23,23,23,38,38,40,40,40,41,41,41,42,42,42,43,43,43,44,44,44,45,45,45,47,47,50,50,50,50,50,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.urls.UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <table>\n            <tbody>\n                <tr>\n                    <td>Id</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n                </tr>\n                <tr>\n                    <td>Имя</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n                </tr>\n                <tr>\n                    <td>Дата создания</td>\n                    <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(LocalDateTimeFormatter.toString(page.getUrl().getCreatedAt()));
				jteOutput.writeContent("</td>\n                </tr>\n            </tbody>\n        </table>\n        <form");
				var __jte_html_attribute_0 = NamedRoutes.urlCheckPath(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"post\">\n            <button type=\"submit\">Проверить</button>\n        </form>\n        <table>\n            <thead>\n                <tr>\n                    <th>Id</th>\n                    <th>Код ответа</th>\n                    <th>title</th>\n                    <th>h1</th>\n                    <th>description</th>\n                    <th>Дата проверки</th>\n                </tr>\n            </thead>\n            <tbody>\n                ");
				for (var urlCheck : page.getUrlChecks()) {
					jteOutput.writeContent("\n                    <tr>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getId());
					jteOutput.writeContent("</td>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getStatusCode());
					jteOutput.writeContent("</td>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getTitle());
					jteOutput.writeContent("</td>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getDescription());
					jteOutput.writeContent("</td>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getId());
					jteOutput.writeContent("</td>\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(LocalDateTimeFormatter.toString(urlCheck.getCreatedAt()));
					jteOutput.writeContent("</td>\n                    </tr>\n                ");
				}
				jteOutput.writeContent("\n            </tbody>\n        </table>\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.urls.UrlPage page = (hexlet.code.dto.urls.UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
