package com.muhlsoftware.wedding

import javax.servlet.http.HttpServletResponse
import pl.touk.excel.export.WebXlsxExporter
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import pl.touk.excel.export.XlsxExporter

class SheetNamedWebXlsxExporter extends XlsxExporter {
	String customSheetName = 'guests'

	SheetNamedWebXlsxExporter() {
		this.workbook = new XSSFWorkbook()
		setUp(workbook)
	}

	SheetNamedWebXlsxExporter(String templateFileNameWithPath) {
		File tmpFile = File.createTempFile('tmpWebXlsx', FILENAME_SUFFIX)
		this.fileNameWithPath = tmpFile.getAbsolutePath()
		this.workbook = copyAndLoad(templateFileNameWithPath, fileNameWithPath)
		this.fileNameWithPath = destinationFileNameWithPath
		this.workbook = createOrLoadWorkbook(destinationFileNameWithPath)
		setUp(workbook)
	}

	SheetNamedWebXlsxExporter setResponseHeaders(HttpServletResponse response) {
		setHeaders(response, new Date().format('yyyy-MM-dd_hh-mm-ss') + FILENAME_SUFFIX)
	}

	SheetNamedWebXlsxExporter setResponseHeaders(HttpServletResponse response, Closure filenameClosure) {
		setHeaders(response, filenameClosure)
	}

	SheetNamedWebXlsxExporter setResponseHeaders(HttpServletResponse response, String filename) {
		setHeaders(response, filename)
	}

	private SheetNamedWebXlsxExporter setHeaders(HttpServletResponse response, def filename) {
		response.setHeader("Content-disposition", "attachment; filename=$filename;")
		response.setHeader("Content-Type", "application/vnd.ms-excel")
		this
	}

	@Override
	private setUp(XSSFWorkbook workbook) {
		this.creationHelper = workbook.getCreationHelper()
		this.sheet = createOrLoadSheet(workbook, this.customSheetName)
		this.dateCellStyle = createDateCellStyle(workbook, XlsxExporter.defaultDateFormat)
	}
}