from pypdf import PdfReader
reader = PdfReader("Plan_de_Création_Plateforme_de_Génération_de_Documents_Éducatifs.pdf")
text = ""
for page in reader.pages:
    text += page.extract_text() + "\n"
with open("pdf_content.txt", "w", encoding="utf-8") as f:
    f.write(text.strip())
