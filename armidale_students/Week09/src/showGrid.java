import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class showGrid extends JPanel
{
	private static ArrayList<String> lines = new ArrayList<String>();

	private static final long serialVersionUID = 1L;
	private static int rows = 0;
	private static int columns = 0;
	private static int GridSize = 0;


	private static ArrayList<Integer> coordX = new ArrayList<Integer>();
	private static ArrayList<Integer> coordY = new ArrayList<Integer>();
	private static ArrayList<String> colors = new ArrayList<String>();

	private static Boolean isColorModelScale = false;
	private static ArrayList<String> colorModel  = new ArrayList<String>();
	private static ArrayList<Double> colorValues  = new ArrayList<Double>();

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int j = 0; j < coordX.size(); j++)
		{
			if (!colorModel.isEmpty())
			{
				int x = coordX.get(j) * GridSize;
				int y = coordY.get(j) * GridSize;
				double value = colorValues.get(j);

				float previousDifference = 0;
				float currentDifference = 0;
				int index = 0;

				for (int i = 0; i < colorValues.size(); i++)
				{
					if (i == 0)
					{
						previousDifference = (float) value;
						currentDifference = (float) value;
					} else if (i == colorValues.size() - 1) {
						index = colorValues.size() - 1;
					} else {
						currentDifference = (float) Math.abs(value - colorValues.get(i));
					}

					if (previousDifference < currentDifference)
					{
						index = i - 1;
						break;
					}

					previousDifference = currentDifference;
				}

				String RGB = colorModel.get(index);
				RGB = RGB.substring(4, RGB.length() - 1);
				int red = 0;
				int green = 0;
				int blue = 0;

				String[] temp;
				temp = RGB.split(",");

				red = Integer.parseInt(temp[0]);
				green = Integer.parseInt(temp[1]);
				blue = Integer.parseInt(temp[2]);

				g.setColor( new Color(red,green,blue));
				g.fillRect(x, y, GridSize, GridSize);
				g.drawRect (x, y, GridSize, GridSize);
			}
			else
			{
				String color = colors.get(j);
				int x = coordX.get(j) * GridSize;
				int y = coordY.get(j) * GridSize;

				switch(color)
				{
				case "red":
					g.setColor(Color.RED);
					break;
				case "blue":
					g.setColor(Color.BLUE);
					break;
				case "green":
					g.setColor(Color.GREEN);
					break;
				}
				g.fillRect(x, y, GridSize, GridSize);
				g.drawRect (x, y, GridSize, GridSize);
			}
		}
	}

	public showGrid()
	{
		super();
		setBackground(new Color(220,217,234));
	}

	public static void main(String[] args) throws IOException
	{
		showGrid panel = new showGrid();
		JFrame application = new JFrame();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.add(panel);

		if (args.length > 0)
		{
			String fileName = "";
			fileName = args[0];
			if (fileName.contains(".txt"))
			{
				File dir = new File(".");
				File fin = new File(dir.getCanonicalPath() + File.separator + fileName);
				readFile(fin);
			}
			else
				System.out.println("File's name is invalid.");
		} else
			readStandardInput();

		for (String line : lines)
		{
			line = line.trim();
			if (line.contains("rgb"))
				isColorModelScale = true;
			else
				isColorModelScale = false;

			if (isColorModelScale)
			{
				String[] temp;
				temp = line.split(" ");
				for (int i =0; i < temp.length ; i++)
				{
					if(temp[i].contains("rgb"))
					{
						colorModel.add(temp[i]);
					}
				}
			}

			if (colorValues.isEmpty() && !isColorModelScale)
			{
				double value = 0;
				for (int i = 1; i < colorModel.size() + 1; i++)
				{
					if (i == 1)
						value = 0;
					else if (i == colorModel.size())
						value = 1;
					else
					{
						value = (float) (i - 1) / (colorModel.size() - 1);
					}
					colorValues.add(value);
				}
			}

			if (line.length() > 3)
			{
				if(line.substring(0, 4).equals("size") && line.charAt(0) != '#')
				{
					line = line.substring(5);

					String[] temp;
					temp = line.split(" ");

					GridSize = Integer.parseInt(temp[2]);
					rows = Integer.parseInt(temp[0]) * GridSize;
					columns = Integer.parseInt(temp[1]) * GridSize;
				}

				if(line.substring(0, 4).equals("cell"))
				{
					int x = 0;
					int y = 0;
					String color = "";
					line = line.substring(5);

					String[] temp;
					temp = line.split(" ");

					x = Integer.parseInt(temp[0]);
					y = Integer.parseInt(temp[1]);
					color = temp[2];

					if (isColorValue(color))
					{
						double value = Float.parseFloat(color);

						coordX.add(x);
						coordY.add(y);
						colorValues.add(value);
					} else if (isColor(color)) {
						coordX.add(x);
						coordY.add(y);
						colors.add(color);
					} else
						System.out.println("Color " + color + " is invalid.");
				}
			}
		}
		application.setSize(columns, rows);
		application.setVisible(true);
	}

	public static Boolean isColor(String color)
	{
		if (color.equals("red") || color.equals("blue") || color.equals("green"))
			return true;
		else
			return false;
	}

	public static Boolean isColorValue(String colorValue)
	{
		if (Character.isDigit(colorValue.charAt(0)))
			return true;
		else
			return false;
	}

	private static void readFile(File fin) throws IOException
	{
		try
		{
			FileInputStream fis = new FileInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		}
		catch (IOException e)
		{
			System.out.println("File does not exit");
		}
	}

	private static void readStandardInput() throws IOException
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);

		String line = null;
		while ((line = stdin.readLine()) != null)
		{
			lines.add(line);
		}
	}
}
