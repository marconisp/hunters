export interface IDoenca {
  id?: number;
  nome?: string;
  sintoma?: string | null;
  precaucoes?: string | null;
  socorro?: string | null;
  obs?: string | null;
}

export class Doenca implements IDoenca {
  constructor(
    public id?: number,
    public nome?: string,
    public sintoma?: string | null,
    public precaucoes?: string | null,
    public socorro?: string | null,
    public obs?: string | null
  ) {}
}

export function getDoencaIdentifier(doenca: IDoenca): number | undefined {
  return doenca.id;
}
