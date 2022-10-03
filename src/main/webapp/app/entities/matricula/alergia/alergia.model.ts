export interface IAlergia {
  id?: number;
  nome?: string;
  sintoma?: string | null;
  precaucoes?: string | null;
  socorro?: string | null;
  obs?: string | null;
}

export class Alergia implements IAlergia {
  constructor(
    public id?: number,
    public nome?: string,
    public sintoma?: string | null,
    public precaucoes?: string | null,
    public socorro?: string | null,
    public obs?: string | null
  ) {}
}

export function getAlergiaIdentifier(alergia: IAlergia): number | undefined {
  return alergia.id;
}
