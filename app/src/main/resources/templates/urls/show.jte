@import java.time.format.DateTimeFormatter
@param hexlet.code.dto.urls.UrlPage page 

@template.layout.page(
    content = @`
        <table>
            <tbody>
                <tr>
                    <td>Id</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${page.getUrl().getFormattedCreatedAt()}</td>
                </tr>
            </tbody>
        </table>
    `
)