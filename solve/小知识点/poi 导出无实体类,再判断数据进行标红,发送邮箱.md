```java
    public String send() {
        byte[] bytes = excelToByteArray(this.
                deviceInspectionMailbox());
        email(bytes);
        return "OK";
    }

    @SneakyThrows
    private void email(byte[] attachment) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(acceptMailConfig.getMail());
        helper.setBcc(acceptMailConfig.getFrom());
        helper.setText("内容：以下因子异常数据（字体标红）。\t\n 土壤温度取值范围：-30℃--70℃\t\n" +
                "土壤湿度取值范围：0---100\t\n" +
                "PH值取值范围：4--10\t\n" +
                "ec值取值范围：0--10000\t\n" +
                "氮磷钾取值范围：1--1999\t\n" +
                "空气温度取值范围：-40--80\t\n" +
                "空气湿度取值范围：0--100\t\n" +
                "光照强度取值范围：0--200000\t\n" +
                "风速取值范围：0--30\t\n" +
                "降水量取值范围：0--200\t\n");
        helper.setSubject("主题：设备巡检"+LocalDateTime.now());
        // 附件
        helper.addAttachment(helper.getMimeMessage().getSubject()+".xlsx", new ByteArrayResource(attachment));
        javaMailSender.send(message);
    }

    /**
     * 将 Excel 写入 ByteArrayOutputStream
     */
    private byte[] excelToByteArray(List<DeviceInspection> list) {
        Map<String, List<DeviceInspection>> collect = list.stream().filter(s -> Objects.nonNull(s.getProductName())).collect(Collectors.groupingBy(DeviceInspection::getProductName));
        if (collect.isEmpty()) {
            return new byte[0];
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             HSSFWorkbook workbook = new HSSFWorkbook()) {
            collect.forEach((k, v) -> createExcelSheet(workbook, k, v));
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private void createExcelSheet(HSSFWorkbook workbook, String k, List<DeviceInspection> v) {
        List<DeviceProperty> properties = metadataService.getProperties(v.get(0).getDeviceId());
        HSSFSheet sheet = workbook.createSheet(k);
        //存放表头的顺序
        List<String> headSort = new ArrayList<>();
        if(!properties.isEmpty()) {
            //生成表头数据
            //第一行作为标题
            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("设备ID");
            title.createCell(1).setCellValue("设备名称");
            title.createCell(2).setCellValue("最新数据时间");
            title.createCell(3).setCellValue("设备状态");
            for (int i = 0; i < properties.size(); i++) {
                title.createCell(i+4).setCellValue(properties.get(i).getPropertyName());
                headSort.add(properties.get(i).getProperty());
            }
        }
        //数据从第二行开始
        final int[] i = {1};
        if(!v.isEmpty()) {
            v.forEach(s->{
                JSONObject jsonObject = JSON.parseObject(s.getRemarks());
                Row dataRow = sheet.createRow(i[0]);
                dataRow.createCell(0).setCellValue(s.getDeviceId());
                dataRow.createCell(1).setCellValue(s.getDeviceName());
                dataRow.createCell(2).setCellValue(s.getLatestDate());
                dataRow.createCell(3).setCellValue(s.getState());
                if(!headSort.isEmpty()) {
                    for (int j = 0; j < headSort.size(); j++) {
                        String value = jsonObject.get(headSort.get(j)) == null ? "无数据" : jsonObject.get(headSort.get(j)).toString();
                        Cell cell = dataRow.createCell(j + 4);
                        cell.setCellValue(value);
                        setColor(workbook, cell, headSort.get(j), value);
                    }
                }
                i[0] +=1;
            });
        }
    }

    /**
     * 单元格的因子判断和设置颜色
     * @param workbook 表
     * @param cell 行
     * @param head 表头因子
     * @param value 因子数值
     */
    private static void setColor(HSSFWorkbook workbook, Cell cell, String head, String value) {
        Map<String, RangeColor> rangeColors = new HashMap<>(12);
        rangeColors.put("tmp", new RangeColor(-40, 80));
        rangeColors.put("hmdy", new RangeColor(0, 100));
        rangeColors.put("lux", new RangeColor(0, 200000));
        rangeColors.put("fdsp", new RangeColor(0, 30));
        rangeColors.put("rain20min", new RangeColor(0, 200));
        rangeColors.put("ec1", new RangeColor(0, 10000));
        rangeColors.put("ph", new RangeColor(4, 10));
        rangeColors.put("hmdy1", new RangeColor(0, 100));
        rangeColors.put("tmp1", new RangeColor(-30, 70));
        rangeColors.put("k", new RangeColor(1, 1999));
        rangeColors.put("p", new RangeColor(1, 1999));
        rangeColors.put("n", new RangeColor(1, 1999));

        if (rangeColors.containsKey(head)) {
            RangeColor rangeColor = rangeColors.get(head);
            if("无数据".equals(value)){
                value = "-1000000";
            }
            double numericValue = Double.parseDouble(value);
            if (numericValue < rangeColor.getLowerBound() || numericValue > rangeColor.getUpperBound()) {
                setCellColor(workbook, cell);
            }
        }
    }

    /**
     * 判断True后设置单元格字体颜色
     */
    private static void setCellColor(HSSFWorkbook workbook, Cell cell) {
        // 获取单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 创建字体
        Font font = workbook.createFont();
        // 设置字体颜色为红色
        font.setColor(IndexedColors.RED.getIndex());
        // 将字体应用于样式
        cellStyle.setFont(font);
        // 设置单元格样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 内部类用于封装范围和颜色信息
     */
    private static class RangeColor {
        private double lowerBound;
        private double upperBound;

        public RangeColor(double lowerBound, double upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        public double getLowerBound() {
            return lowerBound;
        }

        public double getUpperBound() {
            return upperBound;
        }
    }
```
